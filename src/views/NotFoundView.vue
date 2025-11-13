<!-- src/views/NotFoundView.vue -->
<template>
  <div class="min-h-screen bg-gradient-to-br from-[#f0f4ff] via-[#fdf6ff] to-[#fff0f8] flex flex-col justify-center py-16 px-4 overflow-hidden relative">
    <!-- Фоновые декоративные элементы в стиле Дэге -->
    <div class="absolute top-1/4 left-1/6 w-32 h-32 rounded-full bg-[#e0d6ff]/30 blur-3xl animate-pulse-slow"></div>
    <div class="absolute bottom-1/3 right-1/5 w-40 h-40 rounded-full bg-[#ffd6f0]/30 blur-3xl animate-pulse-slow animation-delay-2000"></div>
    <div class="absolute top-1/3 right-1/4 w-16 h-16 rounded-full bg-[#c8e6ff]/40 blur-2xl animate-float"></div>

    <div class="max-w-2xl mx-auto text-center relative z-10">
      <!-- Иконка 404 — мягкая, с перламутровым градиентом -->
      <div class="relative inline-flex items-center justify-center w-28 h-28 rounded-3xl bg-gradient-to-br from-[#e6dfff] via-[#fde8ff] to-[#ffe6f6] mb-8 mx-auto shadow-[0_8px_30px_rgba(150,100,200,0.12)] ring-1 ring-white/40 border border-white/50 backdrop-blur-sm">
        <span class="text-5xl font-bold bg-gradient-to-r from-[#6a5af9] via-[#a66bff] to-[#d966ff] bg-clip-text text-transparent animate-fade-in tracking-tight">
          404
        </span>
        <!-- Пульсирующая "звезда" вместо точки — мягкая, розово-лиловая -->
        <div class="absolute -top-2 -right-2 w-3.5 h-3.5 rounded-full bg-[#d966ff] animate-ping opacity-70"></div>
        <div class="absolute -top-2 -right-2 w-3.5 h-3.5 rounded-full bg-[#d966ff] opacity-100"></div>
      </div>

      <!-- Заголовок — мягкий, с каплей типографической нежности -->
      <h1 class="text-3xl md:text-4xl lg:text-5xl font-medium text-gray-800 mb-5 animate-fade-in-delay-1 leading-tight">
        Страница потерялась в облаках...
      </h1>

      <!-- Описание — поэтичное, как от Дэге -->
      <p class="text-gray-600 max-w-xl mx-auto mb-10 px-2 leading-relaxed animate-fade-in-delay-2 text-sm md:text-base">
        Похоже, вы забрели в уголок интернета, где даже роботы делают перерыв на чай.<br class="hidden sm:block" />
        Но не волнуйтесь — ваши проекты ждут вас дома. Или, может, пора создать что-то новое?
      </p>

      <!-- Кнопки — мягкие, с подчёркнутой тактильностью -->
      <div class="flex flex-col sm:flex-row gap-4 justify-center animate-fade-in-delay-3">
        <router-link
          to="/"
          class="group inline-flex items-center justify-center px-7 py-4 bg-gradient-to-r from-[#6a5af9] via-[#8a6bff] to-[#a66bff] text-white font-medium rounded-2xl shadow-lg hover:shadow-xl transition-all duration-400 transform hover:-translate-y-1 hover:scale-[1.02] active:scale-[0.98] border border-white/20 backdrop-blur-sm"
        >
          <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 mr-2 group-hover:rotate-12 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 00-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
          </svg>
          Мои лендинги
        </router-link>

        <router-link
          to="/templates"
          class="group inline-flex items-center justify-center px-7 py-4 border-2 border-[#a66bff] text-[#6a5af9] font-medium rounded-2xl bg-white/60 backdrop-blur-sm hover:bg-white/80 transition-all duration-300 shadow-md hover:shadow-lg transform hover:-translate-y-0.5"
        >
          <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 mr-2 group-hover:scale-110 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z" />
          </svg>
          Шаблоны
        </router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>
@keyframes fade-in {
  from { opacity: 0; transform: translateY(16px) scale(0.98); }
  to { opacity: 1; transform: translateY(0) scale(1); }
}

@keyframes float {
  0%, 100% { transform: translateY(0px) rotate(0deg); }
  50% { transform: translateY(-12px) rotate(2deg); }
}

@keyframes pulse-slow {
  0%, 100% { opacity: 0.3; transform: scale(1); }
  50% { opacity: 0.5; transform: scale(1.05); }
}

.animate-fade-in {
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) forwards;
}

.animate-fade-in-delay-1 {
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) 0.15s forwards;
  animation-fill-mode: both;
}

.animate-fade-in-delay-2 {
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) 0.3s forwards;
  animation-fill-mode: both;
}

.animate-fade-in-delay-3 {
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) 0.45s forwards;
  animation-fill-mode: both;
}

.animate-float {
  animation: float 8s ease-in-out infinite;
}

.animate-pulse-slow {
  animation: pulse-slow 6s ease-in-out infinite;
}

.animation-delay-2000 {
  animation-delay: 2s;
}
</style>