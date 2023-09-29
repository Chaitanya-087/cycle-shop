import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cycle } from '../models/cycle';

@Injectable({
	providedIn: 'root'
})
export class ApiService {
	private baseUrl = 'http://localhost:8080/api/cycles';

	constructor(private http: HttpClient) { }

	getData(): Observable<Cycle[]> {
		return this.http.get<Cycle[]>(`${this.baseUrl}/list`);
	}

	borrowCycle(cycleId: number): Observable<Cycle[]> {
		return this.http.post<Cycle[]>(`${this.baseUrl}/${cycleId}/borrow`, {});
	}

	returnCycle(cycleId: number): Observable<Cycle[]> {
		return this.http.post<Cycle[]>(`${this.baseUrl}/${cycleId}/return`, {});
	}

	restockCycle(cycleId: number, quantity: number): Observable<Cycle[]> {
		return this.http.post<any[]>(`${this.baseUrl}/${cycleId}/restock`,{}, {params: {quantity: quantity.toString()}});
	}

	getAllOrders(): Observable<Cycle[]> {
		return this.http.get<Cycle[]>(`${this.baseUrl}/orders`);
	}

	getUserOrders(): Observable<Cycle[]> {
		return this.http.get<Cycle[]>(`${this.baseUrl}/orders/user`);
	}
}
