package com.bytecodes.controller;

import com.bytecodes.dto.request.CreateSubscriptionRequest;
import com.bytecodes.dto.response.SubscriptionOperationResponse;
import com.bytecodes.exception.InvalidAccessException;
import com.bytecodes.exception.InvalidSubscriptionException;
import com.bytecodes.exception.SubscriptionAlreadyExistsException;
import com.bytecodes.exception.SubscriptionNotFoundException;
import com.bytecodes.model.ApiUser;
import com.bytecodes.model.Subscription;
import com.bytecodes.model.SubscriptionList;
import com.bytecodes.service.SubscriptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Operation(summary = "Endpoint para obtener las suscripciones activas")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/subscriptions")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Set<SubscriptionList>> getSubscriptions(@AuthenticationPrincipal ApiUser user) {
        var subscriptions = subscriptionService.getSubscriptions(user);
        return ResponseEntity.ok(subscriptions);
    }

    @Operation(summary = "Endpoint para suscribirse a un flujo de notificaciones")
    @SecurityRequirement(name = "JWT")
    @PostMapping("/subscribe")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Subscription> subscribe(@AuthenticationPrincipal ApiUser user,
                                                  @Valid @RequestBody CreateSubscriptionRequest request) throws InvalidSubscriptionException, SubscriptionAlreadyExistsException {
        var subscription = subscriptionService.subscribe(request, user);
        return new ResponseEntity<>(subscription, HttpStatus.CREATED);
    }


    @Operation(summary = "Endpoint para quitar la suscripción de una notificación")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/subscriptions/{subscriptionId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SubscriptionOperationResponse> unsubscribe(@AuthenticationPrincipal ApiUser user,
                                                                     @PathVariable UUID subscriptionId) throws SubscriptionNotFoundException, InvalidAccessException {
        var response = subscriptionService.unsubscribe(subscriptionId, user);
        return ResponseEntity.ok(response);
    }

}
