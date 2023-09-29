import { CartItem } from "./cart-item";

export interface Cart  {
    id: number,
    totalQuantity: number,
    totalPrice: number,
    cartItems: CartItem[]
}