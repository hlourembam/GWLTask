package com.herojit.gwltask.entities

class DashboardEntity {
    //Disease Adapter
    var id = ""
    var user_id = ""
    var title = ""
    var body = ""

    constructor(
        id: String,
        userid: String,
        Title: String,
        Body: String
    ) {
        this.id = id
        this.user_id = userid
        this.title = Title
        this.body = Body
    }
}