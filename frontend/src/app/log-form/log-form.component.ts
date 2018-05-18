import {Component, NgModule, OnInit} from '@angular/core';
import {MAT_TOOLTIP_DEFAULT_OPTIONS} from '@angular/material';
import {LogEntry} from '../log-entry';

@Component({
  selector: 'app-log-form',
  templateUrl: './log-form.component.html',
  styleUrls: ['./log-form.component.css']
})

export class LogFormComponent implements OnInit {

  submitted = false;


  model: LogEntry;

  onSubmit() {
    this.submitted = true;
  }

  constructor() {
  }

  ngOnInit() {
    this.notApplicable();
  }

  // TODO: Remove this when we're done
  get diagnostic() {
    return JSON.stringify(this.model);
  }

  clearForm() {
    this.model = new LogEntry(null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      '');

  }

  notApplicable() {
    this.model = new LogEntry('None',
      'None',
      'None',
      'None',
      0,
      1,
      '1',
      '5/4/18',
      'This is a Default Log Entry');
  }

}


