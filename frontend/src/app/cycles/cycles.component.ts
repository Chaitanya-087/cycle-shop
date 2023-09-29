import { Component } from '@angular/core';
import { Cycle } from '../models/cycle';
import { ApiService } from '../services/cycle.service';
import { AuthService as As} from '../services/auth.service';
import { CartService } from '../services/cart.service';
import { Role } from '../models/role';
import { AuthService } from '@auth0/auth0-angular';

@Component({
  selector: 'app-cycles',
  templateUrl: './cycles.component.html',
  styleUrls: ['./cycles.component.css']
})
export class CyclesComponent {
  data: Cycle[] = []; // Use the Cycle class for data
  roles: typeof Role = Role;
  
  constructor(private api: ApiService,public authService: As,public auth: AuthService, private cartService: CartService) { }

  ngOnInit() {
    this.loadData();
  }

  private loadData() {
    this.api.getData().subscribe(res => this.data = res);
  }

  borrowCycle(cycleId: number): void {
    this.api.borrowCycle(cycleId).subscribe(res => this.data = res);
  }

  returnCycle(cycleId: number): void {
    this.api.returnCycle(cycleId).subscribe(res => this.data = res);
  }

  restockCycle(cycleId: number, quantity: number): void {
    this.api.restockCycle(cycleId, quantity).subscribe(res => this.data = res);
  }

  addToCart(cycleId: number, quantity: number = 1): void {
    this.cartService.addToCart(cycleId,quantity).subscribe(res => alert(`${quantity} cycles added to cart`));
  }
}
