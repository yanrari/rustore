package com.example.rustore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class FullscreenImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Скрываем системные элементы (status bar, navigation bar)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

        setContentView(R.layout.activity_fullscreen_image)

        // Получаем данные из Intent
        val screenshotResIds = intent.getIntArrayExtra("screenshots") ?: intArrayOf()
        val startPosition = intent.getIntExtra("start_position", 0)

        // Находим элементы
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val pageIndicator = findViewById<TextView>(R.id.pageIndicator)

        // Настраиваем ViewPager2
        viewPager.adapter = FullscreenImageAdapter(screenshotResIds.toList())
        viewPager.setCurrentItem(startPosition, false)

        // Обновляем индикатор страницы
        updatePageIndicator(pageIndicator, startPosition + 1, screenshotResIds.size)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updatePageIndicator(pageIndicator, position + 1, screenshotResIds.size)
            }
        })

        // Кнопка назад
        backButton.setOnClickListener {
            finish()
            overridePendingTransition(0, 0) // убираем анимацию
        }

        // Клик по изображению для выхода (опционально)
        viewPager.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }
    }

    private fun updatePageIndicator(textView: TextView, current: Int, total: Int) {
        textView.text = "$current / $total"
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // Поддерживаем полноэкранный режим
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }
}

// Адаптер для ViewPager2
class FullscreenImageAdapter(private val images: List<Int>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<FullscreenImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.fullscreenImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fullscreen_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])

        // Масштабирование с помощью PhotoView (рекомендуется) или простой ImageView
        holder.imageView.setOnClickListener {
            val context = holder.itemView.context
            if (context is FullscreenImageActivity) {
                context.finish()
            }
        }
    }

    override fun getItemCount() = images.size
}
