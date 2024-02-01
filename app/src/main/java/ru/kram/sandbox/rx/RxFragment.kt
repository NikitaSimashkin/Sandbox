package ru.kram.sandbox.rx

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ru.kram.sandbox.R
import ru.kram.sandbox.databinding.FragmentRxBinding

class RxFragment: Fragment(R.layout.fragment_rx) {

	private val binding: FragmentRxBinding by viewBinding(FragmentRxBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		binding.one.setOnClickListener {
			val observable = Observable.just(1, 2, 3)
			val disposable = observable.subscribe {
				Log.d(TAG + "observable just", it.toString())
			}
		}

		binding.two.setOnClickListener {
			val flowable = Flowable.just(1, 2, 3)
			val disposable = flowable.subscribe {
				Log.d(TAG + "flowable just", it.toString())
			}
		}

		binding.three.setOnClickListener {
			val single = Single.just(1)
			val disposable = single.subscribe ({
				Log.d(TAG + "single just", it.toString())
			}, {
				Log.d(TAG + "single just onError", it.toString())
			})
		}
	}

	companion object {
		private const val TAG = "RxFragment"
	}
}