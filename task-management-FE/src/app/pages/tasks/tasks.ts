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

  private extractErrorMessage(err: any, fallback: string): string {
    if (typeof err.error === 'string') {
      try {
        const parsed = JSON.parse(err.error);
        return parsed.error || err.error;
      } catch {
        return err.error;
      }
    }
    return err?.error?.error || fallback;
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
        error: (err) => {
          this.error.set(this.extractErrorMessage(err, 'Failed to load tasks.'));
          this.loading.set(false);
        },
      });
  }

  createTask() {
    if (!this.newTaskName.trim()) return;
    this.error.set(null);

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
      error: (err) => {
        this.error.set(this.extractErrorMessage(err, 'Failed to create task.'));
      },
    });
  }

  updateStatus(task: Task, newStatus: string) {
    this.error.set(null);
    const updated: TaskRequest = { name: task.name, status: newStatus, priority: task.priority };
    this.taskService.updateTask(task.id, updated).subscribe({
      next: () => this.loadTasks(),
      error: (err) => this.error.set(this.extractErrorMessage(err, 'Failed to update task.')),
    });
  }

  deleteTask(id: number) {
    this.error.set(null);
    this.taskService.deleteTask(id).subscribe({
      next: () => this.loadTasks(),
      error: (err) => this.error.set(this.extractErrorMessage(err, 'Failed to delete task.')),
    });
  }
}
