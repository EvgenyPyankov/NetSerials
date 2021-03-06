package com.project.mvc;

import com.project.communication.IdRequest;
import com.project.communication.JsonResponse;
import com.project.model.Subscription;
import com.project.model.Token;
import com.project.model.User;
import com.project.service.TokenService;
import com.project.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@Secured("ROLE_USER")
@RestController(value = "/api")
public class SubscriptionController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    private Logger logger = Logger.getLogger(Subscription.class);

    @RequestMapping(value= "/web")
    @SendTo("/wow")
    public String wow() {
        return "WebSocket";
    }

    @RequestMapping(value = "/getTokens", method = RequestMethod.GET)
    public JsonResponse getTokens() {
        return new JsonResponse(JsonResponse.Status.OK, tokenService.getAll());
    }

    @RequestMapping(value = "/getSubscriptions", method = RequestMethod.GET)
    public JsonResponse getSubscriptions() {
        User user = userService.getCurrentUser();
        Collection<Subscription> subscriptions = user.getSubscriptions();
        return new JsonResponse(JsonResponse.Status.OK, subscriptions);
    }

    @RequestMapping(value = "/addSubscriptionByTokenId", method = RequestMethod.POST)
    public JsonResponse addSubscriptionByTokenId(@RequestBody IdRequest idRequest) {
        Token token = tokenService.get(idRequest.getId());
        User user = userService.getCurrentUser();
        Subscription subscription = new Subscription(token);
        user.getSubscriptions().add(subscription);
        userService.update(user);
        return new JsonResponse(JsonResponse.Status.OK, subscription);
    }

    @RequestMapping(value = "/removeSubscriptionById", method = RequestMethod.POST)
    public JsonResponse removeSubscriptionById(@RequestBody IdRequest idRequest) {
        User user = userService.getCurrentUser();
        Collection<Subscription> subscriptions = user.getSubscriptions();
        for(Subscription subscription : subscriptions) {
            if (subscription.getToken().getId() == idRequest.getId()) {
                subscriptions.remove(subscription);
                userService.update(user);
                return new JsonResponse(JsonResponse.Status.OK, subscription);
            }
        }
        return new JsonResponse(JsonResponse.Status.ERROR, "Not Found subscription with such id.");
    }
}
