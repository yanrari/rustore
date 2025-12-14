package com.example.rustore

import android.app.Activity
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

class AppDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_details)

        // Получаем данные из Intent
        val appId = intent.getLongExtra("app_id", 0)
        val appName = intent.getStringExtra("app_name") ?: "Приложение"
        val appDescription = intent.getStringExtra("app_description") ?: "Описание приложения"
        val appCategory = intent.getStringExtra("app_category") ?: "Категория"
        val appAgeRating = intent.getStringExtra("app_age_rating") ?: "0+"
        val appIconResId = intent.getIntExtra("app_icon_res_id", R.drawable.rustore) // получаем ID иконки

        // Находим все элементы
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val appIcon = findViewById<ImageView>(R.id.appIcon)
        val appNameView = findViewById<TextView>(R.id.appName)
        val appDeveloper = findViewById<TextView>(R.id.appDeveloper)
        val appCategoryView = findViewById<TextView>(R.id.appCategory)
        val appAgeRatingView = findViewById<TextView>(R.id.appAgeRating)
        val installButton = findViewById<com.google.android.material.button.MaterialButton>(R.id.installButton)
        val appDescriptionView = findViewById<TextView>(R.id.appDescription)
        val screenshotsRecyclerView = findViewById<RecyclerView>(R.id.screenshotsRecyclerView)

        // Заполняем данными
        appNameView.text = appName
        appDeveloper.text = "Российский разработчик"
        appCategoryView.text = appCategory
        appAgeRatingView.text = appAgeRating
        appDescriptionView.text = appDescription

        // Устанавливаем иконку приложения (полученную из Intent)
        appIcon.setImageResource(appIconResId)

        // Настройка списка скриншотов
        val screenshots = listOf(
            appIconResId, // используем ту же иконку для примера
            appIconResId,
            appIconResId,
            appIconResId
        )

        screenshotsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        screenshotsRecyclerView.adapter = ScreenshotAdapter(screenshots)

        // Кнопка назад
        backButton.setOnClickListener {
            finish()
        }

        // Кнопка установки
        installButton.setOnClickListener {
            android.widget.Toast.makeText(this, "Установка $appName", android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}

// Простой адаптер для скриншотов
// Простой адаптер для скриншотов
class ScreenshotAdapter(private val screenshots: List<Int>) :
    RecyclerView.Adapter<ScreenshotAdapter.ScreenshotViewHolder>() {

    class ScreenshotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.screenshotImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_screenshot, parent, false)
        return ScreenshotViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScreenshotViewHolder, position: Int) {
        holder.image.setImageResource(screenshots[position])

        // Клик по скриншоту для полноэкранного просмотра
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, FullscreenImageActivity::class.java)

            // Передаем массив ID скриншотов и текущую позицию
            intent.putExtra("screenshots", screenshots.toIntArray())
            intent.putExtra("start_position", position)

            context.startActivity(intent)

        }
    }

    override fun getItemCount() = screenshots.size
}
