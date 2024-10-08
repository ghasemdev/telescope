@file:JsModule("routes-js-module")
@file:JsNonModule

package com.parsuomash.navigation.internal

external fun pushRouteJS(route: String)
external fun popRouteJS()
external fun pushRouteAndroid(route: String)
external fun popRouteAndroid()
external fun hideBottomSheet()
external fun showBottomSheet()
