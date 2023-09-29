import { Cycle } from "./cycle";

export interface CartItem {
    id: number,
    cycle: Cycle,
    quantity: number,
    totalPrice: number
}