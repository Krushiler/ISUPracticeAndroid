package com.bravedeveloper.sandbase.util

import android.os.Bundle
import androidx.core.os.bundleOf

fun Map<String, Any?>.toBundle(): Bundle = bundleOf(*this.toList().toTypedArray())