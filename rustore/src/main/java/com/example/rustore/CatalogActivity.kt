package com.example.rustore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class App(
    val id: Long,
    val name: String,
    val description: String,
    val category: String,
    val ageRating: String,
    val iconResId: Int
)

class AppAdapter(private val apps: List<App>, private val onItemClick: (App) -> Unit) :
    RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.appPlaceholder)
        val name: TextView = itemView.findViewById(R.id.app)
        val desc: TextView = itemView.findViewById(R.id.what)
        val category: TextView = itemView.findViewById(R.id.money)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.store_item, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]
        holder.icon.setImageResource(app.iconResId)
        holder.name.text = app.name
        holder.desc.text = app.description
        holder.category.text = app.category

        // ДОБАВЛЯЕМ ОБРАБОТКУ КЛИКА
        holder.itemView.setOnClickListener {
            onItemClick(app)
        }
    }

    override fun getItemCount() = apps.size
}

// Сама Activity
class CatalogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_fragment)

        // Настраиваем RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.appsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Создаем тестовые данные
        val apps = listOf(
            App(3, "Яндекс Навигатор", "Навигация с учетом пробок", "Транспорт", "12+", R.drawable.icon),
            App(1, "Сбербанк Онлайн", "Управляйте счетами и картами", "Финансы", "0+", R.drawable.icon),
            App(2, "КалькуляторPRO", "Мощный научный калькулятор", "Инструменты", "0+", R.drawable.icon),
            App(4, "Госуслуги", "Государственные услуги онлайн", "Государственные", "16+", R.drawable.icon),
            App(5, "Метро Москвы", "Навигация по метро", "Транспорт", "18+", R.drawable.icon),
            App(6, "Яндекс Карты", "Карты и навигация", "Транспорт", "8+", R.drawable.icon),
            App(7, "ГИБДД", "Проверка штрафов", "Государственные", "0+", R.drawable.icon),
            App(8, "Головоломки 3D", "Решай сложные загадки", "Игры", "6+", R.drawable.icon)
        )

        // СОЗДАЕМ АДАПТЕР С ОБРАБОТКОЙ КЛИКА
        recyclerView.adapter = AppAdapter(apps) { app ->
            // Здесь переходим к карточке приложения
            val intent = android.content.Intent(this@CatalogActivity, AppDetailsActivity::class.java)
            intent.putExtra("app_id", app.id)
            intent.putExtra("app_name", app.name)
            intent.putExtra("app_description", app.description)
            intent.putExtra("app_category", app.category)
            intent.putExtra("app_age_rating", app.ageRating)
            intent.putExtra("app_icon_res_id", app.iconResId) // передаем ID иконки
            startActivity(intent)
        }

        // Обработка клика по кнопке категорий (просто заглушка)
        val categoriesButton = findViewById<com.google.android.material.button.MaterialButton>(R.id.categoriesButton)
        categoriesButton.setOnClickListener {
            val intent = Intent(this@CatalogActivity, CategoriesActivity::class.java)
            startActivity(intent)
        }

    }
}
