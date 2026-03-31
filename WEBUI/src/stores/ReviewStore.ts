import { makeAutoObservable, runInAction } from "mobx";
import { fetchReviewDetail, fetchReviewList } from "../api/reviewApi";
import { ReviewInfo, ReviewQuery } from "../types";

const DEFAULT_QUERY: ReviewQuery = {
  keyword: "",
  reviewNumber: "",
  status: "",
  startDate: "",
  endDate: "",
  page: 1,
  pageSize: 4
};

export class ReviewStore {
  query: ReviewQuery = { ...DEFAULT_QUERY };
  list: ReviewInfo[] = [];
  total = 0;
  selectedId?: number = undefined;
  detail: ReviewInfo | null = null;
  loading = false;
  detailLoading = false;
  error = "";

  constructor() {
    makeAutoObservable(this, {}, { autoBind: true });
  }

  get totalPages(): number {
    return Math.max(1, Math.ceil(this.total / this.query.pageSize));
  }

  setField<K extends keyof ReviewQuery>(field: K, value: ReviewQuery[K]): void {
    this.query[field] = value;
  }

  resetFilters(): void {
    this.query = { ...DEFAULT_QUERY, pageSize: this.query.pageSize };
    this.list = [];
    this.total = 0;
    this.selectedId = undefined;
    this.detail = null;
    this.error = "";
  }

  setPage(page: number): void {
    this.query.page = page;
  }

  select(id?: number): void {
    this.selectedId = id;
  }

  async loadList(): Promise<void> {
    this.loading = true;
    this.error = "";

    try {
      const data = await fetchReviewList(this.query);
      runInAction(() => {
        this.list = data.items;
        this.total = data.total;

        const stillExists = this.selectedId && data.items.some((item) => item.id === this.selectedId);
        if (!stillExists) {
          this.selectedId = data.items[0]?.id;
        }
      });

      if (this.selectedId) {
        await this.loadDetail(this.selectedId);
      } else {
        runInAction(() => {
          this.detail = null;
        });
      }
    } catch (error) {
      runInAction(() => {
        this.error = error instanceof Error ? error.message : "列表加载失败";
        this.list = [];
        this.total = 0;
        this.detail = null;
      });
    } finally {
      runInAction(() => {
        this.loading = false;
      });
    }
  }

  async loadDetail(id: number): Promise<void> {
    this.detailLoading = true;
    this.error = "";
    this.selectedId = id;

    try {
      const data = await fetchReviewDetail(id, this.list);
      runInAction(() => {
        this.detail = data;
      });
    } catch (error) {
      runInAction(() => {
        this.error = error instanceof Error ? error.message : "详情加载失败";
      });
    } finally {
      runInAction(() => {
        this.detailLoading = false;
      });
    }
  }
}
