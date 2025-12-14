package com.example.rustore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Data class для категории
data class AppCategory(
    val id: Long,
    val name: String,
    val appsCount: Int,
    val iconResId: Int
)

// Адаптер для списка категорий
class CategoryAdapter(private val categories: List<AppCategory>, private val onItemClick: (AppCategory) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.categoryIcon)
        val name: TextView = itemView.findViewById(R.id.categoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.icon.setImageResource(category.iconResId)
        holder.name.text = category.name

        holder.itemView.setOnClickListener {
            onItemClick(category)
        }
    }

    override fun getItemCount() = categories.size
}

// Activity для отображения категорий
class CategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        // Находим элементы
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val categoriesRecyclerView = findViewById<RecyclerView>(R.id.categoriesRecyclerView)

        // Настраиваем RecyclerView
        categoriesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Создаем данные для категорий
        val categories = listOf(
            AppCategory(1, "Финансы", 12, R.drawable.icon),
            AppCategory(2, "Инструменты", 8, R.drawable.icon),
            AppCategory(3, "Игры", 25, R.drawable.icon),
            AppCategory(4, "Государственные", 6, R.drawable.icon),
            AppCategory(5, "Транспорт", 9, R.drawable.icon),
            AppCategory(6, "Образование", 7, R.drawable.icon),
            AppCategory(7, "Здоровье", 5, R.drawable.icon),
            AppCategory(8, "Социальные сети", 15, R.drawable.icon)
        )

        // Создаем адаптер
        categoriesRecyclerView.adapter = CategoryAdapter(categories) { category ->
            // Здесь можно реализовать переход к фильтрованному списку приложений
            // или показать Toast для демонстрации
            android.widget.Toast.makeText(
                this,
                "Категория: ${category.name} (${category.appsCount} приложений)",
                android.widget.Toast.LENGTH_SHORT
            ).show()

            // Пример перехода к фильтрованному списку приложений:
            // val intent = Intent(this, CatalogActivity::class.java)
            // intent.putExtra("category_id", category.id)
            // intent.putExtra("category_name", category.name)
            // startActivity(intent)
        }

        // Кнопка назад
        backButton.setOnClickListener {
            finish()
        }
    }
}
