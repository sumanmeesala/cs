package hudson.cli;

import hudson.model.*;
import jenkins.model.Jenkins;
import hudson.model.ListView;
import jenkins.model.GlobalConfiguration;
import javaposse.jobdsl.dsl.DslFactory;


def environment = env
def custName = 'NONE'

def jenkins = Jenkins.instance
def viewName = environment+'_csJobs'
def existingView = jenkins.getView(viewName)

if (existingView == null) {
   def newView = new ListView(viewName)
   jenkins.addView(newView)
   println("View '$viewName' created.")
      println("View '$newView' created.")
   } else {
   println("View '$viewName' already exists.")
    println("View '$newView' already exists.")
  }

matrixJob(environment+'_cs1stjob') {
    description('This is an 1st cc Job DSL job')

    configure { project ->
        project / 'logRotator' {
            numToKeep(4)
                               }
              }
              
    concurrentBuild(true)
    
    scm {
    none()
    }
    
    parameters {
      stringParam('custName',custName,'desc')
      }
      

def globalConfiguration = GlobalConfiguration.all().find { it.displayName == 'Mask Passwords and Regexes' }

if (globalConfiguration) {
           globalConfiguration.setMaskPasswords(true)
           globalConfiguration.setMaskPasswordsConsoleLog(true)
           globalConfiguration.save()
           
           System.setProperty("org.jenkinsci.plugins.workflow.steps.SecretEnvVarHelper.CONFIGURED", "true")
           println("Enabled passwords")
           } else {
            println("Enabled passwords not found")
                   }
                   
    def pName = "${custName}"

    steps {
        shell('echo ${environment} "Im Athna_cc_trigger" ${pName}')
    }
}
                   
def myView = $newView
   println("View ' + $myView + ' created.")
myView.doAddJobToView(environment+'_cs1stjob') 
jenkins.save()


