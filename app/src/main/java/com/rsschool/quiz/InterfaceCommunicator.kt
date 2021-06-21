package com.rsschool.quiz

import androidx.fragment.app.Fragment

interface InterfaceCommunicator {
    open fun openFragment (fragment: Fragment)
}