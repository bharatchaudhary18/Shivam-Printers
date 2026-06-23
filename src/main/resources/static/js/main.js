document.addEventListener('DOMContentLoaded', () => {
  initThemeToggle();
  initStickyNav();
  initScrollReveal();
  initCounters();
  initPortfolioFilter();
  initImageZoom();
  initMobileMenu();
});

function initThemeToggle() {
  const toggle = document.getElementById('theme-toggle');
  const saved = localStorage.getItem('sp-theme');
  if (saved === 'light') document.body.classList.add('light-mode');
  toggle?.addEventListener('click', () => {
    document.body.classList.toggle('light-mode');
    localStorage.setItem('sp-theme', document.body.classList.contains('light-mode') ? 'light' : 'dark');
  });
}

function initStickyNav() {
  const nav = document.getElementById('main-nav');
  if (!nav) return;
  window.addEventListener('scroll', () => {
    nav.classList.toggle('shadow-glow', window.scrollY > 20);
    nav.classList.toggle('py-2', window.scrollY > 20);
    nav.classList.toggle('py-4', window.scrollY <= 20);
  });
}

function initScrollReveal() {
  const reveals = document.querySelectorAll('.reveal');
  const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('visible');
        observer.unobserve(entry.target);
      }
    });
  }, { threshold: 0.1 });
  reveals.forEach(el => observer.observe(el));
}

function initCounters() {
  document.querySelectorAll('[data-counter]').forEach(el => {
    const target = parseInt(el.dataset.counter, 10);
    const duration = 2000;
    const start = performance.now();
    const animate = (now) => {
      const progress = Math.min((now - start) / duration, 1);
      const eased = 1 - Math.pow(1 - progress, 3);
      el.textContent = Math.floor(target * eased).toLocaleString('en-IN');
      if (progress < 1) requestAnimationFrame(animate);
    };
    const observer = new IntersectionObserver(([entry]) => {
      if (entry.isIntersecting) {
        requestAnimationFrame(animate);
        observer.disconnect();
      }
    });
    observer.observe(el);
  });
}

function initPortfolioFilter() {
  const filters = document.querySelectorAll('[data-filter]');
  const items = document.querySelectorAll('[data-category]');
  filters.forEach(btn => {
    btn.addEventListener('click', () => {
      filters.forEach(f => f.classList.remove('active', 'bg-brand-600'));
      btn.classList.add('active', 'bg-brand-600');
      const category = btn.dataset.filter;
      items.forEach(item => {
        const show = category === 'all' || item.dataset.category === category;
        item.style.display = show ? '' : 'none';
        if (show) {
          item.classList.add('animate-fade-in');
        }
      });
    });
  });
}

function initImageZoom() {
  document.querySelectorAll('[data-zoom]').forEach(img => {
    img.addEventListener('click', () => {
      const overlay = document.createElement('div');
      overlay.className = 'fixed inset-0 z-[100] flex items-center justify-center bg-black/90 p-4 cursor-pointer';
      overlay.innerHTML = `<img src="${img.src}" alt="${img.alt}" class="max-h-[90vh] max-w-[90vw] rounded-2xl object-contain"/>`;
      overlay.addEventListener('click', () => overlay.remove());
      document.body.appendChild(overlay);
    });
  });
}

function initMobileMenu() {
  const btn = document.getElementById('mobile-menu-btn');
  const menu = document.getElementById('mobile-menu');
  btn?.addEventListener('click', () => menu?.classList.toggle('hidden'));
}
