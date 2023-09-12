package hudson.cli;

import hudson.model.*
import hudson.matrix.*
import hudson.util.*
import jenkins.model.Jenkins
import hudson.model.ListView
import jenkins.model.GlobalConfiguration
import javaposse.jobdsl.dsl.DslFactory


def environment = env
def custName = 'NONE'

def jenkins = Jenkins.instance
def viewName = environment+'_csJobs'
def existingView = jenkins.getView(viewName)

if (existingView == null) {
                    def newView = new ListView(viewName)
                    jenkins.addView(newView)
                    println("View '$newView' created.")
                          }
else {
   println("View '$viewName' already exists.")
     }


def jobName = environment+'_cs2ndjob'
   
matrixJob(jobName) {

    description('This is an 2nd cc Job DSL job')


          println("job ' + $jobName + ' created-2.")
                  
                   
myView = Jenkins.instance.getView(viewName)
println("View ' + $myView.name + ' created.")

def jobToAdd = Jenkins.instance.getItemByFullName(jobName, Job.class)
println("job ' + $jobName $jobToAdd+ ' created.")
myView.doAddJobToView(jobName) 
myView.save()
jenkins.save()

}
