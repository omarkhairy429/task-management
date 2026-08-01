// src/app/core/task.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

export interface Task {
  id: number;
  name: string;
  status: string;
  priority: string;
}

export interface TaskRequest {
  name: string;
  status?: string;
  priority?: string;
}

@Injectable({ providedIn: 'root' })
export class TaskService {
  private baseUrl = `${environment.apiUrl}/tasks`;

  constructor(private http: HttpClient) {}

  getTasks(priority?: string, status?: string): Observable<Task[]> {
    let params = new HttpParams();
    if (priority) params = params.set('priority', priority);
    if (status) params = params.set('status', status);
    return this.http.get<Task[]>(this.baseUrl, { params });
  }

  createTask(task: TaskRequest): Observable<Task> {
    return this.http.post<Task>(this.baseUrl, task);
  }

  updateTask(id: number, task: TaskRequest): Observable<Task> {
    return this.http.put<Task>(`${this.baseUrl}/${id}`, task);
  }

  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
