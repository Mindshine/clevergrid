define(['angular', 'angularRoute', 'angularMessages', 'bootstrap', 'angularBootstrap', , 'angularBootstrapTpls', 'angularXEditable', 'angularBootstrapColorpicker', 'angularWysiwyg'],function (angular) {
	var app = angular.module('app',['ngRoute', 'xeditable', 'colorpicker.module', 'wysiwyg.module']);
    return app;
});