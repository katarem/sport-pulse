package com.bytecodes.dto.request;

import com.bytecodes.model.NotificationChannel;
import com.bytecodes.model.NotificationEvent;
import com.bytecodes.model.SubscriptionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class CreateSubscriptionRequest {
    @NotNull(message = "El tipo de subscripción no puede ser nulo")
    private SubscriptionType type;
    @Min(value = 1, message = "El ID de equipo solo puede ser positivo")
    private Integer teamId;
    @Min(value = 1, message = "El ID de fixture solo puede ser positivo")
    private Integer fixtureId;
    @NotNull(message = "Se tiene que incluir eventos a suscribir")
    @NotEmpty(message = "Se tiene que incluir al menos 1 evento para suscribirse")
    private Set<NotificationEvent> events;
    @NotNull(message = "Se necesita un canal de Notificación válido: 'WEBHOOK' o 'LOG'")
    private NotificationChannel channel;
    @NotEmpty(message = "Webhook URL no puede estar vacío")
    private String webhookUrl;
}
