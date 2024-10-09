function internalPushRouteJS(route) {
    JSInterface.pushRouteJS(route);
}

function internalPopRouteJS() {
    JSInterface.popRouteJS();
}

function internalPushRouteAndroid(route) {
    JSInterface.pushRouteAndroid(route);
}

function internalPopRouteAndroid() {
    JSInterface.popRouteAndroid();
}

function internalHideBottomSheet() {
    JSInterface.hideBottomSheet();
}

function internalShowBottomSheet() {
    JSInterface.showBottomSheet();
}

module.exports = {
    internalPushRouteJS,
    internalPopRouteJS,
    internalPushRouteAndroid,
    internalPopRouteAndroid,
    internalHideBottomSheet,
    internalShowBottomSheet,
};
