package com.example.kusithms_hdmedi_project.view.diagnosisResult

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.api.RequestBodyModel
import com.example.kusithms_hdmedi_project.databinding.FragmentResultTabABinding
import com.example.kusithms_hdmedi_project.viewmodel.DiagnosisResultViewModel
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.kusithms_hdmedi_project.view.diagnosis.DiagnosisPrevActivity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class ResultTabAFragment : Fragment() {
    private var _binding : FragmentResultTabABinding? = null
    private val viewModel: DiagnosisResultViewModel by viewModels()
    private val binding get() = _binding!!

    //api로부터 권한 요청 결과 콜백으로 수신
    val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){permissions->
        if(permissions.entries.all{it.value}){
            Log.e("success","권한승인")
        }
        else{
            Log.e("fail","권한거부")
        }
    }

    private lateinit var requestBody : RequestBodyModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestBody = arguments?.getSerializable("answer_list") as RequestBodyModel

        _binding = FragmentResultTabABinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //viewModel.postDataApi(requestBody)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wes = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        val storebtn = binding.storebtn
        val scrollview = binding.myscroll
        val minidot = binding.miniDescription
        minidot.getLocationOnScreen(viewModel.location)
        val x = viewModel.location[0]
        val y = viewModel.location[1]

        binding.mainCharacter.startAnimation(
            android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.jump))

        Log.d("taag", requestBody.toString())
        viewModel.postDataApi(requestBody)


        viewModel.totalscore.observe(viewLifecycleOwner, Observer{
                totalscore->
            binding.score.text = totalscore.toString()
        })
        viewModel.careless.observe(viewLifecycleOwner, Observer{
                careless->
            binding.uncarefulScore.text = careless.toString()
        })
        viewModel.impulsitive.observe(viewLifecycleOwner,Observer{
                impulsitive->
            binding.activityScore.text = impulsitive.toString()
        })
        
        //미니 설명창
        minidot.setOnClickListener{
            val dialogFragment = MiniFragment()
            dialogFragment.show(parentFragmentManager,"tag" )
        }

        binding.retrybtn.setOnClickListener{
            val intent = Intent(requireActivity(), DiagnosisPrevActivity::class.java)
            startActivity(intent)
        }

        storebtn.setOnClickListener{
            if(Build.VERSION.SDK_INT < 29){
                if(ContextCompat.checkSelfPermission(
                        requireContext(),
                        wes
                    )!= PackageManager.PERMISSION_GRANTED){
                    //권한 요청 api작동
                    permissionLauncher.launch(arrayOf(wes))
                }
            }
            //현재 화면 비트맵에 그린다
            val bitmap = takeScreenshot(scrollview)
            val uri = saveToGallery(requireContext(), bitmap)
            if(uri != null)
            {
                StoreToast.createToast(requireActivity(), "진단결과가 앨범에 저장되었습니다.")?.show()
            }
            else{
                println("이미지 실패")
            }
        }

        getBoldText(binding.question1content,getString(R.string.howto11),listOf("종합적인 진단"))
        getBoldText(binding.question1content2,getString(R.string.howto22),listOf("약물치료가 가장 효과적인 방법","인지 행동 치료, 환경 조절, 부모 상담을 병행"))
        getBoldText(binding.question1content3,getString(R.string.howto33),listOf("약물치료는 가장 필수적인 치료","규칙적으로 정확하게 처방 받은 용량을 일정 기간 동안 꾸준히 복용"))



    }

    override fun onResume() {
        super.onResume()
        binding.mainCharacter.startAnimation(
            android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.jump))


    }

    companion object {
        fun newInstance(requestBody: RequestBodyModel): ResultTabAFragment {
            val fragment = ResultTabAFragment().apply {
                val bundle = Bundle()
                bundle.putSerializable("answer_list", requestBody)
                arguments = bundle
            }
            return fragment
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun takeScreenshot(scrollView:ScrollView):Bitmap{
        val width = scrollView.getChildAt(0).width
        val height = scrollView.getChildAt(0).height
        var bitmap = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        scrollView.draw(canvas)
        return bitmap
    }

    //주요글자 bold처리 위해
    fun getBoldText(textView: TextView, fullText:String, wordsToBold:List<String>)
    {
        val spannableStringBuilder =  SpannableStringBuilder(fullText)
        for(wordToBold in wordsToBold)
        {
            var startIndex = fullText.indexOf(wordToBold)
            while(startIndex != -1)
            {
                val endIndex = startIndex + wordToBold.length
                spannableStringBuilder.setSpan(StyleSpan(Typeface.BOLD),startIndex,endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                startIndex = fullText.indexOf(wordToBold, endIndex)
            }
        }
        textView.text = spannableStringBuilder
    }

    private fun saveToGallery(context: Context, bitmap:Bitmap ): Uri?{
        val filename = "screenshot_${System.currentTimeMillis()}.png"
        var fos: OutputStream? = null
        var imageUri: Uri? = null

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = resolver.openOutputStream(imageUri!!)
        } else {
            val imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val imageFile = File(imageDir, filename)
            fos = FileOutputStream(imageFile)

            imageUri = Uri.fromFile(imageFile)
        }
        fos.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        return imageUri
    }
}