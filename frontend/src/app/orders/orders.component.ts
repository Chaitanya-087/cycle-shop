import { Component } from '@angular/core';
import { ApiService } from '../services/cycle.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent {
  constructor(private apiService: ApiService) { }
  orders: any[] = [];
  ngOnInit(): void {
    this.getOrders();
  }

  getOrders(): void {
    this.apiService.getAllOrders().subscribe(res => this.orders = res);
  }

}
