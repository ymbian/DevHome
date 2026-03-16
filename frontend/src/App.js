import React, { useState } from 'react';

const stages = [
  { title: '需求阶段', icon: '▤', action: '需求分析', active: true, note: '专家分析优化中' },
  { title: '架构设计阶段', icon: '⌘', action: '架构设计' },
  { title: '规格阶段', icon: '◫', action: '规格设计' },
  { title: '代码阶段', icon: '</>', action: '代码生成' }
];

const teamMembers = [
  { name: '张小凡', role: '项目经理 / 架构师', online: 'online' },
  { name: '李思', role: '前端专家', online: 'online' },
  { name: '王五', role: '后端开发', online: 'idle' },
  { name: '王五', role: '后端开发', online: 'idle' }
];

const timeline = [
  { type: 'upload', title: '张小凡 上传了新需求文档 Core_System_V2.docx', time: '10分钟前' },
  { type: 'done', title: 'AI 完成了 用户模块 的架构设计', time: '1小时前' },
  { type: 'notice', title: '李思 对规格说明提出了修改意见', time: '3小时前' }
];

const metrics = [
  { title: '需求覆盖率', value: '92%', hint: '~+3%', color: 'green' },
  { title: '架构一致性', value: '88%', hint: '稳定', color: 'blue' },
  { title: '代码生成质量', value: 'A-', hint: '需关注', color: 'orange' }
];

const featureCards = [
  {
    eyebrow: 'AUTOMATION',
    title: '自动化转化',
    description: '研发各阶段产物自动衔接，实现从需求模型到代码架构的无缝流转，减少 80% 人工干预。',
    tone: 'blue',
    icon: '⟳'
  },
  {
    eyebrow: 'TRACEABILITY',
    title: '全链路追溯',
    description: '每一行代码、每一个 API 设计都能追溯到最初的业务需求，确保交付物与业务目标完全一致。',
    tone: 'violet',
    icon: '⌘'
  },
  {
    eyebrow: 'HIGH QUALITY',
    title: '高效高质量交付',
    description: '通过 AI 自动化测试与代码质量门禁，显著提升软件交付效率，确保代码符合工业级标准。',
    tone: 'gold',
    icon: '✪'
  }
];

const lifecycleSteps = [
  { title: '产品与需求', description: '需求分析与规格定义', icon: '▣', active: true },
  { title: '架构与设计', description: '系统架构与详细设计', icon: '⌂', active: true },
  { title: '编码与联调', description: '自动化代码生成与调试', icon: '▸', active: true },
  { title: '测试与验收', description: '智能测试与质量验收', icon: '☑' },
  { title: '发布与运维', description: '一键发布与自动化运维', icon: '⇆' },
  { title: '运营与成效', description: '数据运营与成效分析', icon: '△' }
];

const projectTabs = [
  { key: 'all', label: '全部项目', count: 12 },
  { key: 'progress', label: '进行中', count: 8 },
  { key: 'coding', label: '代码阶段', count: 3 },
  { key: 'done', label: '已完成', count: 24 }
];

const projectCards = [
  {
    title: '智能AI助手 2.0 研发',
    subtitle: '新一代自然语言处理核心架构重构',
    progress: 35,
    owner: '张三',
    ownerShort: '张',
    stage: '需求阶段',
    theme: 'blue',
    icon: '🤖'
  },
  {
    title: '云端数据库迁移工程',
    subtitle: '存量千万级数据库平滑迁移至新架构',
    progress: 58,
    owner: '李四',
    ownerShort: '李',
    stage: '设计阶段',
    theme: 'violet',
    icon: '◉'
  },
  {
    title: '权限网关系统重构',
    subtitle: '基于 RBAC 模型的企业级权限管理',
    progress: 82,
    owner: '王五',
    ownerShort: '王',
    stage: '代码阶段',
    theme: 'mint',
    icon: '‹›'
  },
  {
    title: '移动端 App 3.0 UI 规范',
    subtitle: '统一定义移动端视觉与交互规范文档',
    progress: 45,
    owner: '赵六',
    ownerShort: '赵',
    stage: '规格阶段',
    theme: 'peach',
    icon: '📄'
  }
];

const projectStats = [
  { label: '总项目数', value: '36', tone: 'default' },
  { label: '本周发布', value: '4', tone: 'blue' },
  { label: '延迟警报', value: '2', tone: 'red' }
];

const navItems = [
  { key: 'home', label: '首页' },
  { key: 'workspace', label: '工作台' },
  { key: 'projects', label: '项目列表' }
];

function LogoMark() {
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

function HeaderIcon(props) {
  return (
    <button className="icon-button" type="button" aria-label={props.label}>
      {props.children}
    </button>
  );
}

function Avatar(props) {
  return (
    <div className={'avatar avatar-' + props.variant}>
      <div className="avatar-face" />
      <div className="avatar-body" />
    </div>
  );
}

function StageCard(props) {
  return (
    <div className={'stage-card' + (props.active ? ' active' : '')}>
      <div className="stage-heading">
        <span className="stage-icon">{props.icon}</span>
        <div>
          <div className="stage-title">{props.title}</div>
          {props.note ? <div className="stage-note">{props.note} <span className="stage-dot" /></div> : null}
        </div>
      </div>
      <button className={'stage-action' + (props.active ? ' active' : '')} type="button">
        {props.action}
      </button>
    </div>
  );
}

function TeamMember(props) {
  return (
    <div className="team-member">
      <Avatar variant={props.index % 2 === 0 ? 'warm' : 'cool'} />
      <div className="team-copy">
        <div className="team-name">{props.name}</div>
        <div className="team-role">{props.role}</div>
      </div>
      <span className={'status-dot ' + props.online} />
    </div>
  );
}

function TimelineItem(props) {
  return (
    <div className="timeline-item">
      <div className={'timeline-badge ' + props.type}>
        <span />
      </div>
      <div className="timeline-copy">
        <div className="timeline-title">{props.title}</div>
        <div className="timeline-time">{props.time}</div>
      </div>
    </div>
  );
}

function MetricCard(props) {
  return (
    <div className="metric-card">
      <div className="metric-title">{props.title}</div>
      <div className="metric-row">
        <div className="metric-value">{props.value}</div>
        <div className={'metric-hint ' + props.color}>{props.hint}</div>
      </div>
      <div className="metric-bar">
        <div className={'metric-fill ' + props.color} />
      </div>
    </div>
  );
}

function HomeFeatureCard(props) {
  return (
    <article className={'home-feature-card ' + props.tone}>
      <div className="home-feature-visual">
        <div className="home-feature-icon">{props.icon}</div>
        <div className="home-feature-tag">{props.eyebrow}</div>
      </div>
      <h3>{props.title}</h3>
      <p>{props.description}</p>
    </article>
  );
}

function LifecycleStep(props) {
  return (
    <React.Fragment>
      <div className={'lifecycle-step' + (props.active ? ' active' : '')}>
        <div className="lifecycle-icon">{props.icon}</div>
        <div className="lifecycle-title">{props.title}</div>
        <div className="lifecycle-desc">{props.description}</div>
      </div>
      {!props.last ? <div className="lifecycle-arrow">›</div> : null}
    </React.Fragment>
  );
}

function HomePage(props) {
  return (
    <main className="home-page">
      <section className="home-hero">
        <div className="hero-pill">
          <span className="hero-pill-dot" />
          下一代 AI 驱动研发范式
        </div>
        <h1 className="home-title">
          释放开发者的 <span>无限可能</span>
        </h1>
        <p className="home-subtitle">
          以 AI 智能体为核心的端到端协作平台，重塑从需求到交付的完整生命周期。
          <br />
          将繁琐流程自动化，让创意在指尖飞速实现。
        </p>
        <button className="home-cta" type="button" onClick={props.onStart}>
          立即开始
        </button>

        <div className="home-features">
          {featureCards.map(function (card) {
            return (
              <HomeFeatureCard
                key={card.title}
                eyebrow={card.eyebrow}
                title={card.title}
                description={card.description}
                tone={card.tone}
                icon={card.icon}
              />
            );
          })}
        </div>
      </section>

      <section className="lifecycle-section">
        <h2>全生命周期研发流程</h2>
        <p>端到端自动化流转，AI 智能体全方位辅助</p>
        <div className="lifecycle-flow">
          {lifecycleSteps.map(function (step, index) {
            return (
              <LifecycleStep
                key={step.title}
                title={step.title}
                description={step.description}
                icon={step.icon}
                active={step.active}
                last={index === lifecycleSteps.length - 1}
              />
            );
          })}
        </div>
      </section>

      <footer className="home-footer">
        <div className="home-footer-links">
          <button type="button">隐私政策</button>
          <button type="button">服务条款</button>
          <button type="button">关于我们</button>
        </div>
        <div className="home-copyright">© 2024 AI R&amp;D Platform. All rights reserved.</div>
      </footer>
    </main>
  );
}

function WorkspacePage() {
  return (
    <main className="workspace">
      <section className="query-panel">
        <div className="query-label">项目名称</div>
        <input className="query-input" value="统一用户身份验证与授权系统" readOnly />
        <button className="primary-button" type="button">确定</button>
      </section>

      <section className="flow-panel">
        <div className="flow-head">
          <div className="flow-title">研发全生命周期流水线</div>
          <button className="ghost-button" type="button">编辑</button>
        </div>
        <div className="flow-track">
          {stages.map(function (stage, index) {
            return (
              <React.Fragment key={stage.title}>
                <StageCard
                  title={stage.title}
                  icon={stage.icon}
                  action={stage.action}
                  note={stage.note}
                  active={stage.active}
                />
                {index < stages.length - 1 ? <div className="flow-line" /> : null}
              </React.Fragment>
            );
          })}
          <div className="finish-mark">
            <div className="finish-ring">⚑</div>
            <span>结束</span>
          </div>
        </div>
      </section>

      <section className="content-grid">
        <div className="main-column">
          <div className="panel hero-panel">
            <div className="panel-top">
              <div className="inline-field">
                <label>项目名称</label>
                <input value="统一用户身份验证与授权系统" readOnly />
              </div>
              <div className="save-hint">已自动保存于 14:30</div>
              <button className="primary-button" type="button">更新</button>
            </div>

            <div className="assistant-block">
              <div className="assistant-head">
                <div className="assistant-avatar-wrap">
                  <Avatar variant="warm" />
                  <span className="assistant-online" />
                </div>
                <div>
                  <div className="assistant-name">需求专家小张</div>
                  <div className="assistant-tag">数字员工</div>
                </div>
              </div>
              <div className="assistant-message">
                HELLO！我是你的 AI 研发助手。今天有什么可以帮你的？你可以直接描述需求，或者上传 Word/SDD 文档让我进行解析。
              </div>
              <div className="composer">
                <textarea readOnly value="描述您的功能需求或输入指令..." />
                <button className="send-button" type="button">发送 ▷</button>
              </div>
              <div className="upload-drop">
                <div className="upload-cloud">☁</div>
                <div className="upload-title">点击或拖拽文件上传</div>
                <div className="upload-subtitle">支持 Word (.docx), SDD 文档</div>
              </div>
            </div>
          </div>

          <div className="metrics-grid">
            {metrics.map(function (metric) {
              return (
                <MetricCard
                  key={metric.title}
                  title={metric.title}
                  value={metric.value}
                  hint={metric.hint}
                  color={metric.color}
                />
              );
            })}
          </div>
        </div>

        <div className="side-column">
          <div className="panel side-panel">
            <div className="side-panel-head">
              <span>项目团队</span>
              <button className="text-link" type="button">管理</button>
            </div>
            <div className="team-list">
              {teamMembers.map(function (member, index) {
                return (
                  <TeamMember
                    key={member.name + index}
                    name={member.name}
                    role={member.role}
                    online={member.online}
                    index={index}
                  />
                );
              })}
            </div>
            <button className="invite-button" type="button">+ 邀请成员</button>
          </div>

          <div className="panel side-panel activity-panel">
            <div className="side-panel-head">
              <span>最近动态</span>
              <button className="more-button" type="button">•••</button>
            </div>
            <div className="timeline-list">
              {timeline.map(function (item) {
                return <TimelineItem key={item.title} type={item.type} title={item.title} time={item.time} />;
              })}
            </div>
            <button className="history-link" type="button">查看全部历史</button>
          </div>
        </div>
      </section>
    </main>
  );
}

function ProjectListCard(props) {
  return (
    <article className={'project-list-card ' + props.theme}>
      <div className="project-list-visual">
        <div className="project-stage-tag">{props.stage}</div>
        <div className="project-list-icon">{props.icon}</div>
      </div>
      <div className="project-list-body">
        <h3>{props.title}</h3>
        <p>{props.subtitle}</p>
        <div className="project-progress-head">
          <span>当前进度</span>
          <strong>{props.progress}%</strong>
        </div>
        <div className="project-progress-bar">
          <div className={'project-progress-fill ' + props.theme} style={{ width: props.progress + '%' }} />
        </div>
        <div className="project-card-footer">
          <div className="project-owner">
            <span className={'project-owner-badge ' + props.theme}>{props.ownerShort}</span>
            <span>{props.owner}</span>
          </div>
          <button type="button" className="project-detail-link">详情 →</button>
        </div>
      </div>
    </article>
  );
}

function ProjectsPage() {
  return (
    <main className="projects-page">
      <section className="projects-overview">
        <div className="projects-overview-head">
          <div>
            <h1>活跃项目总览</h1>
            <p>当前共有 12 个正在进行的研发项目</p>
          </div>
          <button className="projects-create-button" type="button">＋ 新建项目</button>
        </div>

        <div className="projects-tabs">
          {projectTabs.map(function (tab, index) {
            return (
              <button
                key={tab.key}
                type="button"
                className={'projects-tab' + (index === 0 ? ' active' : '')}
              >
                {tab.label} ({tab.count})
              </button>
            );
          })}
        </div>

        <div className="projects-grid">
          {projectCards.map(function (card) {
            return (
              <ProjectListCard
                key={card.title}
                title={card.title}
                subtitle={card.subtitle}
                progress={card.progress}
                owner={card.owner}
                ownerShort={card.ownerShort}
                stage={card.stage}
                theme={card.theme}
                icon={card.icon}
              />
            );
          })}
        </div>
      </section>

      <footer className="projects-footer-bar">
        {projectStats.map(function (stat) {
          return (
            <div key={stat.label} className="projects-stat">
              <div className="projects-stat-label">{stat.label}</div>
              <div className={'projects-stat-value ' + stat.tone}>{stat.value}</div>
            </div>
          );
        })}
      </footer>
    </main>
  );
}

export default function App() {
  const [activeTab, setActiveTab] = useState('home');

  return (
    <div className={'page-shell' + (activeTab === 'home' ? ' shell-home' : '')}>
      <header className="topbar">
        <div className="brand">
          <div className="brand-badge">
            <LogoMark />
          </div>
          <div className="brand-title">AI研发协作平台</div>
        </div>
        <nav className="nav">
          {navItems.map(function (item) {
            return (
              <button
                key={item.key}
                type="button"
                className={'nav-link' + (activeTab === item.key ? ' active' : '')}
                onClick={function () { setActiveTab(item.key); }}
              >
                {item.label}
              </button>
            );
          })}
        </nav>
        <div className="toolbar">
          <div className="search-box">
            <span className="search-icon">⌕</span>
            <input value="搜索项目" readOnly />
          </div>
          <HeaderIcon label="通知">
            <span className="notice-icon">◷</span>
            <span className="notice-badge" />
          </HeaderIcon>
          <div className="toolbar-divider" />
          <HeaderIcon label="个人中心">
            <span className="user-icon">◦</span>
          </HeaderIcon>
        </div>
      </header>

      {activeTab === 'home' ? <HomePage onStart={function () { setActiveTab('workspace'); }} /> : null}
      {activeTab === 'workspace' ? <WorkspacePage /> : null}
      {activeTab === 'projects' ? <ProjectsPage /> : null}
    </div>
  );
}
