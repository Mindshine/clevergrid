require.config({
    paths: {
		angular: '../bower_components/angular/angular',
		angularRoute: '../bower_components/angular-route/angular-route',
		angularMessages: '../bower_components/angular-messages/angular-messages',
		angularMocks: '../bower_components/angular-mocks/angular-mocks',
        bootstrap: '../bower_components/bootstrap/dist/js/bootstrap.min',
        angularBootstrap: '../bower_components/angular-bootstrap/ui-bootstrap.min',
        angularBootstrapTpls: '../bower_components/angular-bootstrap/ui-bootstrap-tpls.min',
        angularXEditable: '../bower_components/angular-xeditable/dist/js/xeditable',
        angularBootstrapColorpicker: '../bower_components/angular-bootstrap-colorpicker/js/bootstrap-colorpicker-module',
        angularWysiwyg: '../bower_components/angular-wysiwyg/dist/angular-wysiwyg.min',
        jQuery: '../bower_components/jquery/dist/jquery.min',
        config: 'config/main',
        controller: 'controller',
        directive: 'directive',
        factory: 'factory',
        filter: 'filter',
        service: 'service'
    },
    shim: {
		'angular' : {exports : 'angular'},
		'angularRoute': ['angular'],
		'angularMessages': ['angular'],
		'angularMocks': {
			deps:['angular'],
			exports:'angular.mock'
		},
        'bootstrap': ['jQuery'],
        'angularBootstrap': ['angular', 'bootstrap'],
        'angularBootstrapTpls': ['angularBootstrap'],
        'angularXEditable': ['angular'],
        'angularBootstrapColorpicker': ['angular', 'bootstrap'],
        'angularWysiwyg': ['angular', 'angularBootstrapColorpicker', 'jQuery']
	},
	priority: [
		"angular"
	]

});
 
require(['app'], function () {
    require(['config'], function () { 
        angular.bootstrap(document, ['app']);
    });
});