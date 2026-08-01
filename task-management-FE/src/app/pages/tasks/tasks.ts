import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TaskService, Task, TaskRequest } from '../../core/task';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './tasks.html',
  styleUrl: './tasks.css',
})
export class TasksComponent implements OnInit {
  tasks = signal<Task[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);

  statusOptions = ['TODO', 'IN_PROGRESS', 'DONE'];
  priorityOptions = ['LOW', 'MEDIUM', 'HIGH'];

  filterStatus = '';
  filterPriority = '';

  newTaskName = '';
  newTaskStatus = 'TODO';
  newTaskPriority = 'MEDIUM';

  constructor(
    private taskService: TaskService,
    public auth: AuthService,
  ) {}

  ngOnInit() {
    this.loadTasks();
  }

  loadTasks() {
    this.loading.set(true);
    this.error.set(null);
    this.taskService
      .getTasks(this.filterPriority || undefined, this.filterStatus || undefined)
      .subscribe({
        next: (tasks) => {
          this.tasks.set(tasks);
          this.loading.set(false);
        },
        error: () => {
          this.error.set('Failed to load tasks.');
          this.loading.set(false);
        },
      });
  }

  createTask() {
    if (!this.newTaskName.trim()) return;
    const task: TaskRequest = {
      name: this.newTaskName,
      status: this.newTaskStatus,
      priority: this.newTaskPriority,
    };
    this.taskService.createTask(task).subscribe({
      next: () => {
        this.newTaskName = '';
        this.loadTasks();
      },
      error: () => this.error.set('Failed to create task.'),
    });
  }

  updateStatus(task: Task, newStatus: string) {
    const updated: TaskRequest = { name: task.name, status: newStatus, priority: task.priority };
    this.taskService.updateTask(task.id, updated).subscribe({
      next: () => this.loadTasks(),
      error: () => this.error.set('Failed to update task.'),
    });
  }

  deleteTask(id: number) {
    this.taskService.deleteTask(id).subscribe({
      next: () => this.loadTasks(),
      error: () => this.error.set('Failed to delete task.'),
    });
  }
}
