package com.utad.espana.home
import android.net.Uri
import com.utad.espana.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.utad.espana.databinding.FragmentInitBinding
import java.io.File
import java.io.FileOutputStream

class InitFragment : Fragment() {

    private var _binding: FragmentInitBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitBinding.inflate(inflater, container, false)
        val root: View = binding.root



        cargarVideo()

        //FUNCION DEL BOTON
      binding.btnVerComunidades.setOnClickListener {
          val secondFragment = HomeFragment()
          val transaction = parentFragmentManager.beginTransaction()

          transaction.replace(R.id.fcv_initial, secondFragment)
          transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
          transaction.addToBackStack(null)
          transaction.commit()
      }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun cargarVideo() {
        val inputStream = resources.openRawResource(R.raw.hola)
        val outputFile = File(requireActivity().filesDir, "hola.mp4")

        if (!outputFile.exists()) {
            inputStream.use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }
        }


        val videoView = binding.videoView
        val videoUri = Uri.fromFile(outputFile)
        videoView.stopPlayback() // Detiene cualquier reproducción anterior
        videoView.setVideoURI(null) // Limpia cualquier fuente previa
        videoView.setVideoURI(videoUri)




        videoView.setOnPreparedListener {
            videoView.start()
        }
    }

    override fun onStop() {
        super.onStop()
        val videoView = binding.videoView
        videoView?.suspend() // Libera los recursos del VideoView
    }



}