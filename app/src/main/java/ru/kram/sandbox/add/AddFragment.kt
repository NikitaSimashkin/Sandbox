package ru.kram.sandbox.add

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import ru.kram.sandbox.R
import ru.kram.sandbox.databinding.FragmentAddBinding
import ru.kram.sandbox.utils.pxToDp

class AddFragment: Fragment(R.layout.fragment_add) {

	private val binding: FragmentAddBinding by viewBinding(FragmentAddBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		showAdd()
	}

	private fun showAdd() = with(binding) {
		bannerLayout.doOnPreDraw {
			with(banner) {
				setAdSize(AdSize.inlineSize(pxToDp(it.width), pxToDp(it.height)))
				setAdUnitId("demo-banner-yandex")
				setBannerAdEventListener(createBannerAdListener())
				loadAd(AdRequest.Builder().build())
			}
		}
	}

	private fun createBannerAdListener() = object : BannerAdEventListener {
		override fun onAdLoaded() {
			Log.d(TAG, "onAdLoaded")
		}

		override fun onAdFailedToLoad(error: AdRequestError) {
			Log.d(TAG, error.toString())
		}

		override fun onAdClicked() {
			Log.d(TAG, "onAdClicked")
		}

		override fun onLeftApplication() {
			Log.d(TAG, "onLeftApplication")
		}

		override fun onReturnedToApplication() {
			Log.d(TAG, "onReturnedToApplication")
		}

		override fun onImpression(data: ImpressionData?) {
			Log.d(TAG, data.toString())
		}
	}

	companion object {
		private const val TAG = "AddFragment"
	}
}