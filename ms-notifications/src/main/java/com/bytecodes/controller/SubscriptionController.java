package com.bytecodes.controller;

import com.bytecodes.dto.request.CreateSubscriptionRequest;
import com.bytecodes.dto.response.SubscriptionOperationResponse;
import com.bytecodes.model.ApiUser;
import com.bytecodes.model.Subscription;
import com.bytecodes.model.SubscriptionList;
import com.bytecodes.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/subscriptions")
    public ResponseEntity<Set<SubscriptionList>> getSubscriptions(@AuthenticationPrincipal ApiUser user) {
        var subscriptions = subscriptionService.getSubscriptions(user);
        return ResponseEntity.ok(subscriptions);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Subscription> subscribe(@AuthenticationPrincipal ApiUser user,
                                                  @Valid @RequestBody CreateSubscriptionRequest request) {
        var subscription = subscriptionService.subscribe(request, user);
        return ResponseEntity.ok(subscription);
    }

    @DeleteMapping("/subscriptions/{subscriptionId}")
    public ResponseEntity<SubscriptionOperationResponse> unsubscribe(
            @AuthenticationPrincipal ApiUser user,
            @PathVariable UUID subscriptionId) {

        var response = subscriptionService.unsubscribe(subscriptionId, user);
        return ResponseEntity.ok(response);
    }

}
