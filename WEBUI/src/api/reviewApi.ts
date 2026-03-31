import {
  mockReviewListAllResponse,
  mockReviewListAllResponse33,
  mockReviewListAllResponse36,
  mockReviews
} from "../mock/reviews";
import { ReviewInfo, ReviewInfoRaw, ReviewListResponse, ReviewQuery } from "../types";

const DEFAULT_HEADERS = {
  Accept: "application/json"
};

async function requestJson<T>(url: string): Promise<T> {
  const response = await fetch(url, {
    method: "GET",
    headers: DEFAULT_HEADERS
  });

  if (!response.ok) {
    throw new Error(`请求失败: ${response.status}`);
  }

  return response.json() as Promise<T>;
}

function wait(ms: number): Promise<void> {
  return new Promise((resolve) => {
    window.setTimeout(resolve, ms);
  });
}

function normalizeReviewInfo(item: ReviewInfo | ReviewInfoRaw): ReviewInfo {
  return {
    id: Number(item.id || 0),
    reviewNumber: "reviewNumber" in item ? item.reviewNumber || "" : item.review_number || "",
    reviewName: "reviewName" in item ? item.reviewName || "" : item.review_name || "",
    applicant: item.applicant,
    applicantOrg: "applicantOrg" in item ? item.applicantOrg : item.applicant_org,
    reviewType: "reviewType" in item ? item.reviewType : item.review_type,
    reviewExpertList: "reviewExpertList" in item ? item.reviewExpertList : item.review_expert_list,
    groupLeader: "groupLeader" in item ? item.groupLeader : item.group_leader,
    reviewDate: "reviewDate" in item ? item.reviewDate : item.review_date,
    reviewSummary: "reviewSummary" in item ? item.reviewSummary : item.review_summary,
    reviewMinutes: "reviewMinutes" in item ? item.reviewMinutes : item.review_minutes,
    pendingTasks: "pendingTasks" in item ? item.pendingTasks : item.pending_tasks,
    reviewResult: "reviewResult" in item ? item.reviewResult : item.review_result,
    fileName: "fileName" in item ? item.fileName : item.file_name,
    extractTime: "extractTime" in item ? item.extractTime : item.extract_time,
    status: item.status,
    errorMessage: "errorMessage" in item ? item.errorMessage : item.error_message
  };
}

function extractReviewArray(data: unknown): ReviewInfoRaw[] {
  if (Array.isArray(data)) {
    return data as ReviewInfoRaw[];
  }

  if (data && typeof data === "object") {
    const candidate = data as Record<string, unknown>;
    const body = candidate.body as Record<string, unknown> | undefined;
    const nestedBodyData = body?.data;
    if (Array.isArray(nestedBodyData)) {
      return nestedBodyData.map((entry) => {
        if (entry && typeof entry === "object" && "reviewInfo" in (entry as Record<string, unknown>)) {
          return (entry as { reviewInfo: ReviewInfoRaw }).reviewInfo;
        }
        return entry as ReviewInfoRaw;
      });
    }

    const listValue = candidate.list || candidate.data || candidate.items || candidate.rows;
    if (Array.isArray(listValue)) {
      return listValue as ReviewInfoRaw[];
    }
  }

  return [];
}

function shouldUseMockData(): boolean {
  if (typeof window === "undefined") {
    return false;
  }

  const params = new URLSearchParams(window.location.search);
  return params.get("mock") === "1" || window.location.hostname === "localhost";
}

function matchQuery(item: ReviewInfo, query: ReviewQuery): boolean {
  const keyword = query.keyword.trim().toLowerCase();
  const reviewNumber = query.reviewNumber.trim();
  const reviewDate = item.reviewDate || "";

  if (reviewNumber && !item.reviewNumber.includes(reviewNumber)) {
    return false;
  }

  if (keyword) {
    const source = [
      item.reviewNumber,
      item.reviewName,
      item.applicant,
      item.applicantOrg,
      item.fileName
    ]
      .filter(Boolean)
      .join(" ")
      .toLowerCase();

    if (!source.includes(keyword)) {
      return false;
    }
  }

  if (query.status.trim() && item.status !== query.status.trim()) {
    return false;
  }

  if (query.startDate && reviewDate && reviewDate < query.startDate) {
    return false;
  }

  if (query.endDate && reviewDate && reviewDate > query.endDate) {
    return false;
  }

  return true;
}

function getMockList(query: ReviewQuery): ReviewListResponse {
  const filtered = mockReviews.filter((item) => matchQuery(item, query));
  const startIndex = (query.page - 1) * query.pageSize;
  const items = filtered.slice(startIndex, startIndex + query.pageSize);

  return {
    items,
    total: filtered.length
  };
}

async function requestListAllWithMockFallback(): Promise<unknown> {
  if (shouldUseMockData()) {
    await wait(350);
    return mockReviewListAllResponse;
  }

  try {
    return await requestJson<unknown>("/pdf-process/list-all");
  } catch (error) {
    await wait(350);
    return mockReviewListAllResponse;
  }
}

function pickMockResponseByKeyword(keyword: string): unknown {
  const normalizedKeyword = keyword.trim();

  if (normalizedKeyword.includes("2025年第33期")) {
    return mockReviewListAllResponse33;
  }

  if (normalizedKeyword.includes("2025年第36期")) {
    return mockReviewListAllResponse36;
  }

  return mockReviewListAllResponse36;
}

export async function fetchReviewList(query: ReviewQuery): Promise<ReviewListResponse> {
  const response = shouldUseMockData()
    ? await (async () => {
        await wait(350);
        return query.reviewNumber.trim()
          ? pickMockResponseByKeyword(query.reviewNumber || query.keyword)
          : mockReviewListAllResponse;
      })()
    : await requestListAllWithMockFallback();
  const normalizedItems = extractReviewArray(response).map(normalizeReviewInfo);
  const filtered = normalizedItems.filter((item) => matchQuery(item, query));
  const startIndex = (query.page - 1) * query.pageSize;

  return {
    items: filtered.slice(startIndex, startIndex + query.pageSize),
    total: filtered.length
  };
}

export async function fetchReviewDetail(id: number, sourceList?: ReviewInfo[]): Promise<ReviewInfo> {
  const currentSource = sourceList && sourceList.length > 0 ? sourceList : mockReviews;
  const localMatch = currentSource.find((review) => review.id === id);

  if (localMatch) {
    return Promise.resolve(localMatch);
  }

  if (shouldUseMockData()) {
    const item = mockReviews.find((review) => review.id === id);
    if (!item) {
      throw new Error("未找到对应的模拟记录");
    }
    return Promise.resolve(item);
  }

  return requestJson<ReviewInfo>(`/api/review-info/${id}`);
}
