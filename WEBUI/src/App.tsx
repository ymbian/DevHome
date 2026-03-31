import React, { FormEvent, useEffect, useMemo } from "react";
import { observer } from "mobx-react";
import { Link, Route, Switch, useHistory, useLocation, useParams } from "react-router-dom";
import { ReviewStore } from "./stores/ReviewStore";

const store = new ReviewStore();

function useSelectedId(): number | undefined {
  const { id } = useParams<{ id?: string }>();
  if (!id) {
    return undefined;
  }
  const parsed = Number(id);
  return Number.isNaN(parsed) ? undefined : parsed;
}

const DashboardPage = observer(() => {
  const history = useHistory();
  const location = useLocation();
  const selectedId = useSelectedId();

  useEffect(() => {
    if (selectedId) {
      void store.loadDetail(selectedId);
      return;
    }

    store.select(undefined);
  }, [selectedId]);

  const summaryText = useMemo(() => {
    if (store.loading) {
      return "正在同步数据库中的 PDF 评审记录...";
    }
    return `当前共检索到 ${store.total} 条记录，支持按编号、名称、申请人和 PDF 文件名进行关键字查询。`;
  }, [store.loading, store.total]);

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    store.setPage(1);
    void store.loadList();
  };

  const handleReset = () => {
    store.resetFilters();
    history.replace("/reviews");
    void store.loadList();
  };

  const handleSelect = (id: number) => {
    history.push(`/reviews/${id}${location.search}`);
    void store.loadDetail(id);
  };

  const handlePageChange = (page: number) => {
    store.setPage(page);
    void store.loadList();
  };

  return (
    <div className="page-shell shell-home">
      <header className="topbar">
        <div className="brand">
          <div className="brand-badge">
            <LogoMark />
          </div>
          <div className="brand-title">架构评审信息库</div>
        </div>
        <nav className="nav">
          <Link to="/reviews" className="nav-link active">评审信息库</Link>
          <span className="nav-meta">自动抽取入库</span>
        </nav>
        <div className="toolbar">
          <label className="search-box">
            <span>⌕</span>
            <input
              type="text"
              value={store.query.keyword}
              placeholder="搜索编号、名称、申请人、PDF"
              onChange={(event) => store.setField("keyword", event.target.value)}
            />
          </label>
          <HeaderIcon label="提醒">
            <span className="notice-badge" />
            ◔
          </HeaderIcon>
          <div className="toolbar-divider" />
          <HeaderIcon label="个人中心">○</HeaderIcon>
        </div>
      </header>

      <main className="workspace home-page review-home-page">
        <section className="home-hero review-hero">
          <div className="hero-tech-tag">ARCH REVIEW / STRUCTURED INDEX</div>
          <h1 className="home-title">
            <span className="title-main">架构评审</span>
            <span className="title-accent">一目了然</span>
          </h1>
          <p className="home-subtitle">
            {summaryText}
            <br />
            聚合评审编号、专家列表、纪要、结论和原始 PDF 文件名，集中呈现架构评审资料。
          </p>
        </section>

        <section className="review-workbench">
          <form className="query-card review-query-card" onSubmit={handleSubmit}>
            <div className="section-heading">
              <div>
                <h2>筛选条件</h2>
                <p>支持按评审编号、状态、日期范围和关键字快速过滤。</p>
              </div>
              <div className="table-badge">架构评审资料</div>
            </div>

            <div className="query-grid">
              <label className="field">
                <span>评审编号</span>
                <input
                  type="text"
                  value={store.query.reviewNumber}
                  placeholder="如：2025年第36期"
                  onChange={(event) => store.setField("reviewNumber", event.target.value)}
                />
              </label>

              <label className="field">
                <span>处理状态</span>
                <select
                  value={store.query.status}
                  onChange={(event) => store.setField("status", event.target.value)}
                >
                  <option value="">全部状态</option>
                  <option value="SUCCESS">SUCCESS</option>
                  <option value="FAILED">FAILED</option>
                  <option value="PROCESSING">PROCESSING</option>
                </select>
              </label>

              <label className="field">
                <span>评审开始日期</span>
                <input
                  type="date"
                  value={store.query.startDate}
                  onChange={(event) => store.setField("startDate", event.target.value)}
                />
              </label>

              <label className="field">
                <span>评审结束日期</span>
                <input
                  type="date"
                  value={store.query.endDate}
                  onChange={(event) => store.setField("endDate", event.target.value)}
                />
              </label>

              <label className="field">
                <span>每页数量</span>
                <select
                  value={String(store.query.pageSize)}
                  onChange={(event) => store.setField("pageSize", Number(event.target.value))}
                >
                  <option value="4">4</option>
                  <option value="10">10</option>
                  <option value="20">20</option>
                  <option value="50">50</option>
                </select>
              </label>
            </div>

            <div className="query-actions">
              <button className="home-cta query-submit" type="submit" disabled={store.loading}>
                {store.loading ? "查询中..." : "查询记录"}
              </button>
              <button className="btn btn-outline" type="button" onClick={handleReset}>
                重置条件
              </button>
            </div>
          </form>

          {store.error ? <div className="status-banner error">{store.error}</div> : null}

          <section className="content-grid">
            <div className="panel">
              <div className="section-heading panel-heading">
                <div>
                  <h2>结果列表</h2>
                  <p>共 {store.total} 条记录，点击任意卡片查看完整字段。</p>
                </div>
              </div>

              {store.loading ? (
                <div className="empty-state">正在加载评审记录，请稍候。</div>
              ) : store.list.length === 0 ? (
                <div className="empty-state">没有查到匹配数据，请调整筛选条件后重试。</div>
              ) : (
                <div className="result-list">
                  {store.list.map((item) => (
                    <article
                      key={item.id}
                      className={`result-card ${store.selectedId === item.id ? "active" : ""}`}
                      onClick={() => handleSelect(item.id)}
                    >
                      <div className="result-card-top">
                        <strong>{item.reviewNumber || "-"}</strong>
                        <span className={`status-tag status-${(item.status || "unknown").toLowerCase()}`}>
                          {item.status || "UNKNOWN"}
                        </span>
                      </div>
                      <div className="meta-row"><span>评审编号</span>{item.reviewNumber || "-"}</div>
                      <div className="meta-row"><span>文件名</span>{item.fileName || "-"}</div>
                      <div className="meta-row"><span>评审名称</span>{item.reviewName || "-"}</div>
                      <div className="meta-row"><span>申请人</span>{item.applicant || "-"}</div>
                      <div className="meta-row meta-row-block">
                        <span>评审简介</span>
                        <p>{item.reviewSummary || "-"}</p>
                      </div>
                      <div className="meta-row meta-row-block">
                        <span>评审结论</span>
                        <p>{item.reviewResult || "-"}</p>
                      </div>
                      <div className="result-actions">
                        <button
                          type="button"
                          className="btn btn-secondary detail-trigger"
                          onClick={(event) => {
                            event.stopPropagation();
                            handleSelect(item.id);
                          }}
                        >
                          查看详情
                        </button>
                      </div>
                    </article>
                  ))}
                </div>
              )}

              <div className="pagination">
                <button
                  className="btn btn-secondary"
                  type="button"
                  disabled={store.query.page <= 1 || store.loading}
                  onClick={() => handlePageChange(store.query.page - 1)}
                >
                  上一页
                </button>
                <span>
                  第 {store.query.page} / {store.totalPages} 页
                </span>
                <button
                  className="btn btn-secondary"
                  type="button"
                  disabled={store.query.page >= store.totalPages || store.loading}
                  onClick={() => handlePageChange(store.query.page + 1)}
                >
                  下一页
                </button>
              </div>
            </div>

            <div className="panel detail-panel">
              <div className="section-heading panel-heading">
                <div>
                  <h2>记录详情</h2>
                  <p>{store.detail?.id ? `当前查看 ID ${store.detail.id}` : "选择一条记录查看详情"}</p>
                </div>
              </div>

              {store.detailLoading ? (
                <div className="empty-state">正在读取详情...</div>
              ) : !store.detail ? (
                <div className="empty-state">选择左侧一条记录后，这里会展示 PDF 解析后的全部字段。</div>
              ) : (
                <div className="detail-layout">
                  <div className="detail-kpis">
                    <div className="kpi-card">
                      <span>评审编号</span>
                      <strong>{store.detail.reviewNumber || "-"}</strong>
                    </div>
                    <div className="kpi-card">
                      <span>评审类型</span>
                      <strong>{store.detail.reviewType || "-"}</strong>
                    </div>
                    <div className="kpi-card">
                      <span>组长</span>
                      <strong>{store.detail.groupLeader || "-"}</strong>
                    </div>
                    <div className="kpi-card">
                      <span>提取时间</span>
                      <strong>{store.detail.extractTime || "-"}</strong>
                    </div>
                  </div>

                  <div className="detail-section">
                    <h3>基础信息</h3>
                    <div className="detail-grid">
                      <InfoItem label="评审名称" value={store.detail.reviewName} />
                      <InfoItem label="申请人" value={store.detail.applicant} />
                      <InfoItem label="申请科室" value={store.detail.applicantOrg} />
                      <InfoItem label="评审日期" value={store.detail.reviewDate} />
                      <InfoItem label="原始PDF文件名" value={store.detail.fileName} />
                      <InfoItem label="处理状态" value={store.detail.status} />
                    </div>
                  </div>

                  <TextSection title="评审专家列表" content={store.detail.reviewExpertList} />
                  <TextSection title="评审简介" content={store.detail.reviewSummary} />
                  <TextSection title="评审纪要" content={store.detail.reviewMinutes} />
                  <TextSection title="待跟踪事项" content={store.detail.pendingTasks} />
                  <TextSection title="评审结论" content={store.detail.reviewResult} />

                  {store.detail.errorMessage ? (
                    <div className="detail-section">
                      <h3>错误信息</h3>
                      <div className="text-block error-block">{store.detail.errorMessage}</div>
                    </div>
                  ) : null}
                </div>
              )}
            </div>
          </section>
        </section>
      </main>
    </div>
  );
});

function LogoMark(): JSX.Element {
  return (
    <div className="logo-mark">
      <span className="logo-node logo-node-top" />
      <span className="logo-node logo-node-left" />
      <span className="logo-node logo-node-right" />
      <span className="logo-line logo-line-left" />
      <span className="logo-line logo-line-right" />
    </div>
  );
}

function HeaderIcon(props: { label: string; children: React.ReactNode }): JSX.Element {
  return (
    <button className="icon-button" type="button" aria-label={props.label}>
      {props.children}
    </button>
  );
}

function InfoItem(props: { label: string; value?: string }): JSX.Element {
  return (
    <div className="info-item">
      <span>{props.label}</span>
      <strong>{props.value || "-"}</strong>
    </div>
  );
}

function TextSection(props: { title: string; content?: string }): JSX.Element {
  return (
    <div className="detail-section">
      <h3>{props.title}</h3>
      <div className="text-block">{props.content || "暂无内容"}</div>
    </div>
  );
}

export function App(): JSX.Element {
  return (
    <Switch>
      <Route path="/reviews/:id?">
        <DashboardPage />
      </Route>
      <Route path="/">
        <DashboardPage />
      </Route>
    </Switch>
  );
}
