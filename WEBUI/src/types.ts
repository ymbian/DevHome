export interface ReviewInfo {
  id: number;
  reviewNumber: string;
  reviewName: string;
  applicant?: string;
  applicantOrg?: string;
  reviewType?: string;
  reviewExpertList?: string;
  groupLeader?: string;
  reviewDate?: string;
  reviewSummary?: string;
  reviewMinutes?: string;
  pendingTasks?: string;
  reviewResult?: string;
  fileName?: string;
  extractTime?: string;
  status?: string;
  errorMessage?: string;
}

export interface ReviewQuery {
  keyword: string;
  reviewNumber: string;
  status: string;
  startDate: string;
  endDate: string;
  page: number;
  pageSize: number;
}

export interface ReviewListResponse {
  items: ReviewInfo[];
  total: number;
}

export interface ReviewInfoRaw {
  id?: number;
  review_number?: string;
  review_name?: string;
  applicant?: string;
  applicant_org?: string;
  review_type?: string;
  review_expert_list?: string;
  group_leader?: string;
  review_date?: string;
  review_summary?: string;
  review_minutes?: string;
  pending_tasks?: string;
  review_result?: string;
  file_name?: string;
  extract_time?: string;
  status?: string;
  error_message?: string;
}
