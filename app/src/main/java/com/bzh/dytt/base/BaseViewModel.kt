package com.bzh.dytt.base

import android.arch.lifecycle.ViewModel
import com.bzh.dytt.repository.DataRepository
import javax.inject.Inject

open class BaseViewModel @Inject constructor(var dataRepository: DataRepository) : ViewModel()
