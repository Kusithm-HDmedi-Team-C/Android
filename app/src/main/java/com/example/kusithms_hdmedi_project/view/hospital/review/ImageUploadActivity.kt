package com.example.kusithms_hdmedi_project.view.hospital.review

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.base.BaseDialog
import com.example.kusithms_hdmedi_project.databinding.ActivityImageUploadBinding
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import java.io.File

class ImageUploadActivity : AppCompatActivity() {
    private var _binding : ActivityImageUploadBinding? = null
    private val binding get() = _binding!!
    private val STORAGE_PERMISSION_CODE = 1
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvUploadImage.setOnClickListener {
            val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
            if (permission == PackageManager.PERMISSION_GRANTED) {
                // 권한이 이미 허용되어 있는 경우
                val intent = Intent(Intent.ACTION_PICK)
                intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                intent.type = "image/*"
                startActivityForResult(intent, STORAGE_PERMISSION_CODE)
            } else {
                // 권한이 허용되지 않은 경우, 권한을 요청합니다.
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), STORAGE_PERMISSION_CODE)
            }
        }

        binding.btnClose.setOnClickListener {
            BaseDialog(
                context = this@ImageUploadActivity,
                title = resources.getString(R.string.후기_작성을_종료하시겠습니까_),
                subTitle = "",
                positive = {
                    finish()
                },
                negative = {}
            ).show(supportFragmentManager, "")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == STORAGE_PERMISSION_CODE && data!=null && data.data != null) {
            Toast.makeText(this,"사진 업로드가 완료되었습니다.", Toast.LENGTH_SHORT).show()
//            bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, data.data!!))
            imageUploadComplete()
        }
    }

    private fun imageUploadComplete() {
        binding.llImageUploadOff.visibility = View.INVISIBLE
        binding.llImageUploadOn.visibility = View.VISIBLE
        binding.tvWriteReview.apply {
            isClickable = true
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@ImageUploadActivity, R.color.main_blue))

            setOnClickListener {
                val intent = Intent(this@ImageUploadActivity, WriteReviewActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                intent.type = "image/*"
                startActivityForResult(intent, STORAGE_PERMISSION_CODE)
            } else {
                Toast.makeText(this,"이미지 업로드를 위해 접근권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}