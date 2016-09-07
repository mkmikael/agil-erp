package com.gfaces.components

/**
 * Created by mkmik on 07/09/2016.
 */
enum StateType {

    INFO('info', 'glyphicon glyphicon-info-sign'),
    DANGER('danger', 'glyphicon glyphicon-exclamation-sign'),
    WARNING('warning', 'glyphicon glyphicon-warning-sign'),
    SUCCESS('success', 'glyphicon glyphicon-ok-sign')

    String label
    String icon

    StateType(String label, String icon) {
        this.label = label
        this.icon = icon
    }
}