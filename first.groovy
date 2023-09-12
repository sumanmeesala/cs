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


def jobName = environment+'_cs1stjob'
   
matrixJob(jobName) {

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
                             }
    else {
            println("Enabled passwords not found")
         }
                   
    def pName = "${custName}"

    steps {
        shell('echo ${environment} "Im Athna_cc_trigger" ${pName}')
println("job ' + $jobName + ' created-1.")
          }
          println("job ' + $jobName + ' created-2.")
                  }
                   

jenkins.save()


