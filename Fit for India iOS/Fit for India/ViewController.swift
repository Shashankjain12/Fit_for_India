//
//  ViewController.swift
//  Fit for India
//
//  Created by Rudrank Riyam on 12/01/19.
//  Copyright © 2019 Hackathon. All rights reserved.
//

import UIKit
import CoreMotion
import UICircularProgressRing

class ViewController: UIViewController {
    @IBOutlet weak var Steps: UICircularProgressRing!
    
    @IBOutlet weak var activityTypeLabel: UILabel!
    private let activityManager = CMMotionActivityManager()
    private let pedometer = CMPedometer()
    
    @IBOutlet weak var fitCoinCounter: UILabel!
    @IBOutlet weak var stepsCounter: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    func updateFitCoin() {
        if stepsCounter.text == "100" {
            self.fitCoinCounter.text = "FitCoin : 1"
        }
    }
    private func startCountingSteps() {
        pedometer.startUpdates(from: Date()) {
            [weak self] pedometerData, error in
            guard let pedometerData = pedometerData, error == nil else { return }
            
            DispatchQueue.main.async {
                self?.stepsCounter.text = pedometerData.numberOfSteps.stringValue
            }
        }
    }

    private func startUpdating() {
        if CMMotionActivityManager.isActivityAvailable() {
            startTrackingActivityType()
        }
        
        if CMPedometer.isStepCountingAvailable() {
            startCountingSteps()
        }
    }
    
    private func startTrackingActivityType() {
        activityManager.startActivityUpdates(to: OperationQueue.main) {
            [weak self] (activity: CMMotionActivity?) in
            
            guard let activity = activity else { return }
            DispatchQueue.main.async {
                if activity.walking {
                    self?.activityTypeLabel.text = "Walking"
                } else if activity.stationary {
                    self?.activityTypeLabel.text = "Stationary"
                } else if activity.running {
                    self?.activityTypeLabel.text = "Running"
                }
            }
        }
    }

}


