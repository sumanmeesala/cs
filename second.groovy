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


def jobName = environment+'_cs2ndjob'
   
matrixJob(jobName) {

    description('This is an 2nd cc Job DSL job')


          println("job ' + $jobName + ' created-2.")
                  
myView = hudson.model.Hudson.instance.getView(viewName)
 println("view ' +$myView $viewName $jobName + ' job")
 
  if (viewName instanceof ListView && jobName != null) {
     println("job ' + $viewName $jobName + ' adding -3.")
viewName.doAddJobToView(jobName) 
viewName.save()
} 
else {
newV = hudson.model.Hudson.instance.getView(viewName)
def newJ = environment+'_cs2ndjob'
   println("job ' + $newV $newJ + ' adding -4.")
newV.doAddJobToView(newJ) 
newV.save()
}
jenkins.save()

}
