import { Component } from '@angular/core';
import { ApiService } from '../services/cycle.service';

@Component({
  selector: 'app-user-orders',
  templateUrl: './user-orders.component.html',
  styleUrls: ['./user-orders.component.css']
})
export class UserOrdersComponent {
  constructor(private apiService: ApiService) { }

  orders: any[] = [];
  ngOnInit(): void {
    this.getOrders();
  }

  getOrders(): void {
    this.apiService.getUserOrders().subscribe(res => this.orders = res);
  }
}
