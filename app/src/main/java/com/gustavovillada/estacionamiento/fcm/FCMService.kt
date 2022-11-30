package com.gustavovillada.icesistapp.main.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.gustavovillada.icesistapp.main.utils.NotificationUtil
import java.text.DecimalFormat

class FCMService: FirebaseMessagingService() {

    private val TYPE_NOTIFICATION_ORDER=1
    private val TYPE_NOTIFICATION_REVISION_STATE=2

    override fun onMessageReceived(message: RemoteMessage) {
/*
        val typeNotification= getTypeNotification(message)


        when(typeNotification){
            TYPE_NOTIFICATION_REVISION_STATE->{

                val modification = getModificationByMessageReceived(message)
                val title=getTitleByModification(modification)
                val message=getMessageByModification(modification)

                NotificationUtil.showNotification(this,title,message)
            }

            TYPE_NOTIFICATION_ORDER->{
                val order = getOrderByMessageReceived(message)
                val title=getTitleByOrder(order)
                val message=getMessageByOrder(order)
                NotificationUtil.showNotification(this,title,message)
            }
        }*/

    }
/*
    private fun getTypeNotification(message: RemoteMessage):Int{
        val idOrder= message.data["idOrder"]
        return if (idOrder==""){
            TYPE_NOTIFICATION_REVISION_STATE
        }else{
            TYPE_NOTIFICATION_ORDER
        }
    }

    private fun getOrderByMessageReceived(message: RemoteMessage): Order{
        val idEmprendimiento= message.data["idEmprendimiento"]
        val photoEmprendimiento= message.data["photoEmprendimiento"]
        val nameEmprendimiento= message.data["nameEmprendimiento"]
        val idOrder= message.data["idOrder"]
        val idUser= message.data["idUser"]
        val summaryProducts= message.data["summaryProducts"]
        val ubication= message.data["ubication"]
        val sellerName= message.data["sellerName"]
        val state= Integer.parseInt(message.data["state"])
        val total= Integer.parseInt(message.data["total"])
        val isActive= true//message.data["active"] as Boolean

        return Order(0,idEmprendimiento,photoEmprendimiento,nameEmprendimiento,idOrder,idUser,summaryProducts,total,state,ubication,sellerName,isActive)

    }


    /**
     * Este método se encarga de adaptar el titulo de la notificación dependiendo del estado del pedido.
     */
    private fun getTitleByOrder(data: Order): String {
        if (data.state==Order.ESTADO_ENVIADO){
            return "Nuevo pedido"
        }else if(data.state==Order.ESTADO_CANCELADO){
            return "Pedido Cancelado."
        }else if(data.state==Order.ESTADO_RECHAZADO){
            return "Pedido Rechazado"

        }else if(data.state==Order.ESTADO_ACEPTADO){
            return "Pedido Aceptado"

        }else if(data.state==Order.ESTADO_ENTREGADO){
            return "Pedido Entregado"

        }else{
            return "Pedido No entregado"
        }
    }


    /**
     * Este método se encarga de adaptar el mensaje de la notificación de acuerdo al pedido.
     */
    private fun getMessageByOrder(data: Order): String {

        val df = DecimalFormat("$ #,###.###")


        if (data.state==Order.ESTADO_ENVIADO){
            return "Has recibido un pedido por valor de "+df.format(data.total)+" para llevar a ${data.ubication}."
        }else if(data.state==Order.ESTADO_CANCELADO){
            return "El cliente ha cancelado el pedido por valor de "+df.format(data.total)
        }else if(data.state==Order.ESTADO_RECHAZADO){
            return "El pedido en ${data.nameEmprendimiento} ha sido cancelado."

        }else if(data.state==Order.ESTADO_ACEPTADO){
            return "Tu pedido en ${data.nameEmprendimiento} ha sido aceptado."

        }else if(data.state==Order.ESTADO_ENTREGADO){
            return "Tu pedido ordenado a ${data.nameEmprendimiento} ha sido entregado satisfactoriamente. !Calificalo y comenta algo positivo!"

        }else{
            return "Tu pedido ordenado a ${data.nameEmprendimiento} no ha sido entregado."
        }
    }


    private fun getModificationByMessageReceived(message: RemoteMessage): Modification{
        val idModification= message.data["idModification"]
        val stateModification= Integer.parseInt(message.data["stateModification"])
        val typeModification= Integer.parseInt(message.data["typeModification"])

    //product
        val nameProduct= message.data["name"]

        val product=Product("","","","",nameProduct,"","",0,5F,true,true)

        return Modification(idModification,stateModification,typeModification,"",product)

    }


    /**
     * Este método se encarga de adaptar el titulo de la notificación dependiendo del estado del pedido.
     */
    private fun getTitleByModification(data: Modification): String {

        when(data.typeModification){

            Modification.MODIFICATION_TYPE_ADD_PRODUCT->{
                return "Solicitud de agregar producto."
            }
            Modification.MODIFICATION_TYPE_MODIFIED_PRODUCT->{
                return "Solicitud de editar producto."
            }
            Modification.MODIFICATION_TYPE_REMOVE_PRODUCT->{
                return "Solicitud de eliminar producto."
            }

            else->{
                return "Solicitud"
            }

        }
    }


    /**
     * Este método se encarga de adaptar el mensaje de la notificación de acuerdo al pedido.
     */
    private fun getMessageByModification(data: Modification): String {

        when(data.typeModification){

            Modification.MODIFICATION_TYPE_ADD_PRODUCT->{
                if (data.typeModification==Modification.MODIFICATION_STATE_ACEPTED){
                    return "Tu solicitud para AGREGAR "+data.name+" ha sido ACEPTADA."
                }else{
                    return "Tu solicitud para AGREGAR "+data.name+" ha sido RECHAZADA."
                }
            }
            Modification.MODIFICATION_TYPE_MODIFIED_PRODUCT->{
                if (data.typeModification==Modification.MODIFICATION_STATE_ACEPTED){
                    return "Tu solicitud para EDITAR "+data.name+" ha sido ACEPTADA."
                }else{
                    return "Tu solicitud para EDITAR "+data.name+" ha sido RECHAZADA."
                }
            }
            Modification.MODIFICATION_TYPE_REMOVE_PRODUCT->{
                if (data.typeModification==Modification.MODIFICATION_STATE_ACEPTED){
                    return "Tu solicitud para ELIMINAR "+data.name+" ha sido ACEPTADA."
                }else{
                    return "Tu solicitud para ELIMINAR "+data.name+" ha sido RECHAZADA."
                }
            }

            else->{
                return "SOMA ha respondido a tu solicitud."
            }
        }

    }
*/
}