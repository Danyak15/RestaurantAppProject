package com.example.restaurantapp.presentation.profile

import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.restaurantapp.R
import com.example.restaurantapp.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    private var cameraImageUri: Uri? = null
    private var cameraImageFile: File? = null
    private var hasLocalAvatar = false

    private val pickAvatarLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { selectedUri ->
            val avatarFile = copyAvatarToCache(selectedUri)
            showAvatar(selectedUri)
            viewModel.uploadAvatar(avatarFile)
        }
    }

    private val takeAvatarLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        val uri = cameraImageUri
        val file = cameraImageFile

        if (success && uri != null && file != null) {
            showAvatar(uri)
            viewModel.uploadAvatar(file)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!viewModel.checkAuth()) {
            findNavController().navigate(
                R.id.action_profileFragment_to_loginFragment
            )
            return
        }

        setupClicks()
        observeViewModel()
        showAvatarPlaceholder()

        viewModel.getMe()
    }

    private fun setupClicks() {
        binding.avatarContainer.setOnClickListener {
            showAvatarSourceDialog()
        }

        binding.btnExit.setOnClickListener {
            viewModel.clearSession()
            findNavController().navigate(
                R.id.action_profileFragment_to_loginFragment
            )
        }

        binding.btnChange.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_personInfoFragment
            )
        }

        binding.btnFavorite.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_favoriteDishesFragment
            )
        }

        binding.btnReservations.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_myReservationsFragment
            )
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.user.collect { user ->
                        if (user != null) {
                            binding.tvUserName.text = "${user.name} ${user.surname}".trim()
                            binding.tvPhone.text = user.phone
                            binding.tvPoints.text = user.loyaltyPoints.toString()
                            binding.tvLevel.text = user.loyaltyLevel

                            if (!hasLocalAvatar) {
                                showAvatarUrl(user.avatarUrl)
                            }
                        }
                    }
                }

                launch {
                    viewModel.message.collect { message ->
                        Toast.makeText(
                            requireContext(),
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showAvatarSourceDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Фото профиля")
            .setItems(arrayOf("Выбрать из галереи", "Сделать фото")) { _, which ->
                when (which) {
                    0 -> pickAvatarLauncher.launch("image/*")
                    1 -> launchCamera()
                }
            }
            .show()
    }

    private fun launchCamera() {
        val file = createAvatarFile()
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            file
        )

        cameraImageFile = file
        cameraImageUri = uri
        takeAvatarLauncher.launch(uri)
    }

    private fun createAvatarFile(): File {
        val directory = File(requireContext().cacheDir, "profile_images")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        return File(directory, "avatar_${System.currentTimeMillis()}.jpg").apply {
            createNewFile()
        }
    }

    private fun copyAvatarToCache(uri: Uri): File {
        val extension = when (requireContext().contentResolver.getType(uri)) {
            "image/png" -> "png"
            "image/webp" -> "webp"
            else -> "jpg"
        }
        val file = File(
            File(requireContext().cacheDir, "profile_images").apply { mkdirs() },
            "avatar_${System.currentTimeMillis()}.$extension"
        )

        requireContext().contentResolver.openInputStream(uri).use { input ->
            file.outputStream().use { output ->
                requireNotNull(input).copyTo(output)
            }
        }

        return file
    }

    private fun showAvatar(uri: Uri) {
        hasLocalAvatar = true
        binding.ivAvatar.imageTintList = null
        binding.ivAvatar.setPadding(0, 0, 0, 0)
        binding.ivAvatar.setImageURI(uri)
    }

    private fun showAvatarUrl(url: String?) {
        if (url.isNullOrBlank()) {
            showAvatarPlaceholder()
            return
        }

        val fullUrl = if (url.startsWith("http")) {
            url
        } else {
            "http://10.0.2.2:8080$url"
            "http://192.168.0.101:8080$url"
        }

        binding.ivAvatar.imageTintList = null
        binding.ivAvatar.setPadding(0, 0, 0, 0)
        binding.ivAvatar.load(fullUrl) {
            crossfade(true)
            error(android.R.drawable.ic_menu_camera)
            fallback(android.R.drawable.ic_menu_camera)
        }
    }

    private fun showAvatarPlaceholder() {
        val padding = (38 * resources.displayMetrics.density).toInt()
        binding.ivAvatar.setPadding(padding, padding, padding, padding)
        binding.ivAvatar.imageTintList = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), R.color.text_primary)
        )
        binding.ivAvatar.setImageResource(android.R.drawable.ic_menu_camera)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
