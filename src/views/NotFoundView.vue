<!-- src/views/NotFoundView.vue -->
<template>
  <div class="min-h-screen bg-gradient-to-b from-indigo-50 via-white to-fuchsia-50 flex flex-col justify-center py-16 px-4">
    <div class="max-w-2xl mx-auto text-center">
      <!-- Иконка 404 с анимацией -->
      <div class="relative inline-flex items-center justify-center w-24 h-24 rounded-2xl bg-gradient-to-br from-indigo-100 to-fuchsia-100 mb-8 mx-auto shadow-lg ring-1 ring-white/30">
        <span class="text-5xl font-extrabold bg-gradient-to-r from-indigo-600 via-purple-600 to-fuchsia-600 bg-clip-text text-transparent animate-fade-in">
          404
        </span>
        <!-- Пульсирующая точка -->
        <div class="absolute -top-2 -right-2 w-3 h-3 rounded-full bg-fuchsia-500 animate-ping"></div>
        <div class="absolute -top-2 -right-2 w-3 h-3 rounded-full bg-fuchsia-500"></div>
      </div>

      <!-- Заголовок -->
      <h1 class="text-3xl md:text-4xl lg:text-5xl font-bold mb-5 animate-fade-in-delay-1">
        Страница не найдена
      </h1>

      <!-- Описание -->
      <p class="text-gray-600 max-w-xl mx-auto mb-10 px-2 leading-relaxed animate-fade-in-delay-2">
        Запрашиваемая страница была удалена, переименована или временно недоступна.<br class="hidden sm:block" />
        Не переживайте — вы можете вернуться к своим проектам или создать новый лендинг.
      </p>

      <!-- Кнопки -->
      <div class="flex flex-col sm:flex-row gap-4 justify-center animate-fade-in-delay-3">
        <router-link
          to="/"
          class="group inline-flex items-center justify-center px-7 py-4 bg-gradient-to-r from-indigo-600 via-purple-600 to-fuchsia-600 text-white font-semibold rounded-xl shadow-xl hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-1 hover:scale-[1.02] active:scale-[0.98]"
        >
          <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 mr-2 group-hover:rotate-12 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 00-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
          </svg>
          Мои лендинги
        </router-link>

        <router-link
          to="/templates"
          class="group inline-flex items-center justify-center px-7 py-4 border-2 border-indigo-500 text-indigo-700 font-semibold rounded-xl bg-white/70 backdrop-blur-sm hover:bg-indigo-50 transition-all duration-300 shadow-md hover:shadow-lg transform hover:-translate-y-0.5"
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
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

.animate-fade-in {
  animation: fade-in 0.6s ease-out forwards;
}

.animate-fade-in-delay-1 {
  animation: fade-in 0.6s ease-out 0.1s forwards;
  animation-fill-mode: both;
}

.animate-fade-in-delay-2 {
  animation: fade-in 0.6s ease-out 0.2s forwards;
  animation-fill-mode: both;
}

.animate-fade-in-delay-3 {
  animation: fade-in 0.6s ease-out 0.3s forwards;
  animation-fill-mode: both;
}
</style>