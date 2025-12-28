package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

public class CheckoutService {

    private PriceValidatorService priceValidatorService;
    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart) {
        // Implementation for checkout process
        startTimer();
        List<CartItem> priceValidationList = cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> {
                    boolean isValidPrice = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isValidPrice);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());
        if(!priceValidationList.isEmpty()){
            return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
        }
        timeTaken();
        return new CheckoutResponse(CheckoutStatus.SUCCESS);
    }
}
