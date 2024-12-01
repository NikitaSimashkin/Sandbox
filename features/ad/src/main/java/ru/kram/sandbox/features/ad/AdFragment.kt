package ru.kram.sandbox.features.ad

import android.os.Bundle
import android.view.View
import ru.kram.sandbox.common.utils.pxToDp
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import ru.kram.sandbox.features.ad.databinding.FragmentAdBinding
import timber.log.Timber

class AdFragment: Fragment(R.layout.fragment_ad) {

	private val binding: FragmentAdBinding by viewBinding(FragmentAdBinding::bind)

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
            Timber.tag(TAG).d("onAdLoaded")
		}

		override fun onAdFailedToLoad(error: AdRequestError) {
            Timber.tag(TAG).d(error.toString())
		}

		override fun onAdClicked() {
            Timber.tag(TAG).d("onAdClicked")
		}

		override fun onLeftApplication() {
            Timber.tag(TAG).d("onLeftApplication")
		}

		override fun onReturnedToApplication() {
            Timber.tag(TAG).d("onReturnedToApplication")
		}

		override fun onImpression(data: ImpressionData?) {
            Timber.tag(TAG).d(data.toString())
		}
	}

	companion object {
		private const val TAG = "AddFragment"
	}
}