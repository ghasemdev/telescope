function pushRouteJS(route) {
    JSInterface.pushRouteJS(route);
}

function popRouteJS() {
    JSInterface.popRouteJS();
}

function pushRouteAndroid(route) {
    JSInterface.pushRouteAndroid(route);
}

function popRouteAndroid() {
    JSInterface.popRouteAndroid();
}

function hideBottomSheet() {
    JSInterface.hideBottomSheet();
}

function showBottomSheet() {
    JSInterface.showBottomSheet();
}

module.exports = {
    pushRouteJS,
    popRouteJS,
    pushRouteAndroid,
    popRouteAndroid,
    hideBottomSheet,
    showBottomSheet,
};
