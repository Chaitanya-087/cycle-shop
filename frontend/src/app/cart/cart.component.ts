import { Component, OnInit } from '@angular/core';
import { CartService } from '../services/cart.service';
import { Cart } from '../models/cart';
import { AuthService } from '@auth0/auth0-angular';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cart?: Cart;

  constructor(private cartService: CartService, public auth: AuthService) { }
  ngOnInit(): void {
    this.getCart();
  }

  getCart(): void {
    this.cartService.getCart().subscribe(cart => this.cart = cart);
  }

  removeFromCart(id: number, quantity: number = 1): void {
    this.cartService.removeFromCart(id, quantity).subscribe(res => this.cart = res)
  }

  checkoutFromCart(id: number): void {
    this.cartService.checkoutFromCart(id).subscribe(res => this.cart = res);
  }

  checkoutAll(): void {
    this.cartService.checkoutAll().subscribe(res => this.cart = res);
  }
}
