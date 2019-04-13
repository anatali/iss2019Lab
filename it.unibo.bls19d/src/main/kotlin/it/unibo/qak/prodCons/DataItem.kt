package it.unibo.qak.prodCons

data class  DataItem( val item: String, val id : String = DataItem.id ){
    companion object { val id = "dataItem"  }
}