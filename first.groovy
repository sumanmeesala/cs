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

   println("existing View '$existingView' .")
   
if (existingView == null) {
                    def newView = new ListView(viewName)
                    jenkins.addView(newView)
                    println("View '$newView' created.")
                          }
else {
   println("View '$viewName' already exists.")
     }


def jobName = environment+'_cs1stjob'
   
matrixJob(jobName) {

    description('This is an 1st cc Job DSL job')


          println("job ' + $jobName + ' created-2.")
              
    // Set log rotation
    job.buildDiscarder = new LogRotator(4, -1, -1, -1)

    // Add a string parameter
    job.addProperty(new ParametersDefinitionProperty(
        new StringParameterDefinition("custName", "NONE")
    ))

    // Enable concurrent builds
    job.concurrentBuild = true

    // Configure source code management (none)

    // Define the axes for the Matrix Job

    // Add an Execute Shell build step
    job.getBuildersList().add(new Shell("echo 'Hello, Jenkins! This is ${environment}'"))

jenkins.save()

}
