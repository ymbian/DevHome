(function () {
  var path = window.location.pathname || '';
  path = path.replace(/\/$/, '') || '/';
  var designPage = path.indexOf('/design') !== -1;
  document.querySelectorAll('.app-sidebar a[data-nav]').forEach(function (a) {
    var nav = a.getAttribute('data-nav');
    var isActive = (nav === 'dev' && ((path === '/' || path === '') || designPage)) ||
                   (nav === 'tech' && path.indexOf('/tech') !== -1) ||
                   (nav === 'work-plan' && path.indexOf('/work-plan') !== -1);
    if (isActive) a.classList.add('active'); else a.classList.remove('active');
  });
})();
