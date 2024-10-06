function pushRouteJS(route) {
    JSInterface.pushRoute(route);
}

function popRouteJS() {
    JSInterface.popRoute();
}

module.exports = {
    pushRouteJS,
    popRouteJS,
};
