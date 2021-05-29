package com.elacqua.opticmap.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elacqua.opticmap.databinding.FragmentOcrBinding
import com.elacqua.opticmap.ocr.MLTranslator
import com.elacqua.opticmap.util.Constant
import com.elacqua.opticmap.util.SharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class OcrFragment : Fragment() {

    private var binding: FragmentOcrBinding? = null
    private var image: Bitmap? = null
    private var langFrom: String = Constant.DEFAULT_LANGUAGE
    private var langTo: String = Constant.DEFAULT_LANGUAGE
    private val translator = MLTranslator()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs()
        observeTranslatedText()
    }

    private fun observeTranslatedText() {
        translator.translatedText.observe(viewLifecycleOwner, {
            binding?.txtTranslationResult?.text = it
            translator.close()
        })
    }

    private fun getArgs() {
        image = arguments?.get(Constant.OCR_IMAGE_KEY) as Bitmap
        val pref = SharedPref(requireContext())
        langFrom = pref.langFrom
        langTo = pref.langTo
    }

    private fun getTextFromImage() {
        if (image == null) {
            Timber.e("image is null")
            return
        }

        binding?.imgOcrPicture?.setImageBitmap(image)
        CoroutineScope(Dispatchers.Default).launch {
//            val ocrResult = ...
//            withContext(Dispatchers.Main) {
//                translator.translate(ocrResult)
//                binding?.txtOcrResult?.text = ocrResult
//            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOcrBinding.inflate(inflater, container, false)
        return binding!!.root
    }
}